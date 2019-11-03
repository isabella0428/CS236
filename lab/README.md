## Cloud Computing Lab

##### 实验目的

本次实验主要利用了Spark云平台和开源图计算平台GraphX实现了SSSP(Single Source Shortest Path) 和 PageRank算法。



##### 实验数据

- 数据集来源：**[SNAP networks](http://snap.stanford.edu/data/index.html)** ，分成 `wiki-Vote.txt `和`web-Google.txt`
- 实验一中分别使用了`wiki-Vote.txt` 和 `web-Google.txt`
- 实验二中使用了`web-Google.txt`



##### 实验环境要求

- JAVA开发环境
- hadoop， Spark
- GraphX



##### 文件说明

```
├── lab1															//SSSP 实验
│   ├── project												//sbt 生成文件
│   ├── src														//代码
│   │ 	├── main
│   │ 	│		├── resources
│   │ 	│		│		├── wiki-Vote.txt			// wikipedia数据集
│   │ 	│		│		├── Google						// Google数据集
│   │ 	│		├── scala
│   │ 	│		│		├── SSSP.scala				// 基于Spark实现SSSP
│   ├── target
│   ├── build.sbt
│   └── lab1.sh
├── lab2															//PageRank 实验
│   ├── project												//sbt 生成文件
│   ├── src														//代码
│   │ 	├── main
│   │ 	│		├── resources
│   │ 	│		│		├── wiki-Vote.txt			// wikipedia数据集
│   │ 	│		├── scala
│   │ 	│		│		├── pageRank.scala    // 基于Graphx实现PageRank算法
│   ├── target
│   ├── build.sbt
│   └── lab2.sh                
└── Report.md                  // 实验报告
```



##### 代码运行

##### SSSP

进入实验文件夹, 运行脚本

```
cd lab1
bash lab1.sh
```



##### PageRank

进入实验文件夹, 运行脚本

```
cd lab2
bash lab2.sh
```



