package org.kin.bigdata.spark.kafka.writer

import org.apache.kafka.clients.producer.{Callback, ProducerRecord}
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

/**
  * Created by huangjianqin on 2017/11/7.
  * 参考自https://github.com/BenFradet/spark-kafka-writer
  */
class RDDKafkaWriter[T: ClassTag](@transient private val rdd: RDD[T]) extends KafkaWriter[T] with Serializable{
  override def write2Kafka[K, V](
                                  producerConfig: Map[String, Object],
                                  transformFunc: (T) => ProducerRecord[K, V],
                                  callback: Option[Callback]): Unit = {
    rdd.foreachPartition{pattern =>
      val producer = KafkaProducerCache.getProducer[K, V](producerConfig)
      pattern.foreach{
        record => producer.send(transformFunc(record), callback.orNull)
      }
    }
  }
}
