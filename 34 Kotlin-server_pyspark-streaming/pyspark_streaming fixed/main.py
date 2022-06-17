import json
import os

from pyspark import SparkContext
from pyspark.storagelevel import StorageLevel
from pyspark.streaming.context import StreamingContext

os.environ["PYSPARK_PYTHON"] = "python3"
os.environ["PYSPARK_DRIVER_PYTHON"] = "python3"


def myPrint(obj: list) -> None:
    for elem in obj:
        print("URL:" + elem["url"], "\tData: " + str(elem["datetime"]), "\tTitlu: "+elem["headline"])


def main() -> None:
    ctx = SparkContext(appName="streaming", master="local[*]")
    ctx.setLogLevel("WARN")
    storage_level = StorageLevel(True, True, False, False, 1)
    spark_context = StreamingContext(ctx, 3)
    data = spark_context.socketTextStream("localhost", 8888, storageLevel = storage_level)  # storageLevel = storage_level
    json_data = data\
        .map(lambda x: json.loads(x)) \
        .filter(lambda jsonData: len(jsonData["url"]) <= 100)\
        .filter(lambda jsonData: jsonData["image"][-3:] != "png")

    json_data.foreachRDD(lambda rdd: myPrint(rdd.collect()))

    spark_context.start()
    spark_context.awaitTermination()
    spark_context.stop()


if __name__ == "__main__":
    main()
