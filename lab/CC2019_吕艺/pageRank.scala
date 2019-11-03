/**
 * PageRank Algorithm based on  wiki_Vote dataset
 *
 * Author: Yi Lyu
 * Date: October 20, 2019
 * Email: isabella_aus_china@sjtu.edu.cn
 */

import org.apache.spark.graphx.{Graph, VertexId}
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.graphx._

object PageRank {
  def main(args: Array[String]) {
    // Define  APP name
    val appName = "PageRank"
    // Use 40 threads locally
    val master = "local[40]"  
    // Create new spark context
    val conf = new SparkConf().setAppName(appName).setMaster(master)
    val sc = new SparkContext(conf)
    // Define Damping Ratio
    val q = 0.85;

    // Create the graph with  wiki-Vote.txt
    val graph: Graph[Int, Int] = 
      GraphLoader.edgeListFile(sc, "/home/lxiang_stu3/Isabella/lab1/src/main/resources/wiki-Vote.txt")

    // Given a graph where the vertex property is the out degree
    val inputGraph = graph.outerJoinVertices(graph.outDegrees)((vid, _, degOpt) => degOpt.getOrElse(0))
    // Construct a graph where each edge contains the weight
    // and each vertex is the initial PageRank
    var pageRankGraph = inputGraph.mapTriplets(triplet => 1.0 / triplet.srcAttr).mapVertices((id, _) => 1.0)

    // vertex count
    val vNum = inputGraph.vertices.count()

    // Repeat PageRank calculation for 100 times
    for (iteration <- 1 to 100) {
        pageRankGraph.cache()
        val rankUpdates = pageRankGraph.aggregateMessages[Double] (
            triplet => {
                triplet.sendToDst(triplet.attr *  triplet.srcAttr);
            },
            _ + _,
            TripletFields.Src  // Fields to access
          )

        // Update Graph with 
        pageRankGraph = pageRankGraph.outerJoinVertices(rankUpdates) {
            (id, oldPageRank, msgSumUpdates) => (1 - q) / vNum + q * msgSumUpdates.getOrElse(0.0)
        }
        
        println(s"PageRank Iterations $iteration.")
      }

    // Print top20 Popular Pages
    pageRankGraph.vertices.sortBy(
            pair => -pair._2
        ).map {
            case (id, attr) => id
        }.take(20).foreach(println)
    }
}
