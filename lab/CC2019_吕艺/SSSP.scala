/**
 * Single source shortest path using Pregel
 *
 * Author: Yi Lyu
 * Date: October 20, 2019
 * Email: isabella_aus_china@sjtu.edu.cn
 */

import org.apache.spark.graphx.{Graph, VertexId}
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object SSSP {
  def main(args: Array[String]) {
    // Define  APP name
    val appName = "SSSP"
    // Use 40 threads locally
    val master = "local[40]"  
    // Create new spark context
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    val sc = new SparkContext(conf)

    // Create the graph with  wiki-Vote.txt
    val graph: Graph[Int, Int] = 
      GraphLoader.edgeListFile(sc, "/home/lxiang_stu3/Isabella/lab1/src/main/resources/wiki-Vote.txt")
    val sourceId: VertexId = 32   // The ultimate source
    // Initialize the graph such that all vertices except the root have distance infinity.
    val initialGraph = graph.mapVertices((id, _) =>
        if (id == sourceId) 0.0 else Double.PositiveInfinity)

    // Start forwarding the messages and calculate the  minumun distance
    val sssp = initialGraph.pregel(Double.PositiveInfinity)(
      (id, dist, newDist) => math.min(dist, newDist), // Vertex Program
      triplet => {  // Send Message
        if (triplet.srcAttr + triplet.attr < triplet.dstAttr) {
          Iterator((triplet.dstId, triplet.srcAttr + triplet.attr))
        } else {
          Iterator.empty
        }
      },
      (a, b) => math.min(a, b) // Merge Message
    )
    println(sssp.vertices.collect.mkString("\n"))
  }
}
