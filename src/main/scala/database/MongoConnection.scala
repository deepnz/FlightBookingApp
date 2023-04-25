package scala.database

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros.createCodecProvider
import org.mongodb.scala.MongoClient
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result.{DeleteResult, UpdateResult}

@Singleton
class MongoDBConnector @Inject()(config: Configuration) {

  private val connectionString = new ConnectionString(config.getString("mongodb.uri"))
  private val client: MongoClient = MongoClient(connectionString)
  private val database: MongoDatabase = client.getDatabase(config.getString("mongodb.database"))

  // Codecs for serializing/deserializing case classes to BSON documents
  private val codecRegistry = DEFAULT_CODEC_REGISTRY
    .register(createCodecProvider[Flight]())
    .register(createCodecProvider[Booking]())
    .register(createCodecProvider[Account]())
    .register(createCodecProvider[PaymentInfo]())

  def getCollection[T](collectionName: String): MongoCollection[T] = {
    database.getCollection[T](collectionName).withCodecRegistry(codecRegistry)
  }

  def closeConnection(): Unit = {
    client.close()
  }
}
