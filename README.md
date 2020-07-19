
###Version
```
Java Version: 1.8
Maven Version 3.3.9
```

### Installing Kinesis Connector
```
wget https://archive.apache.org/dist/flink/flink-1.8.2/flink-1.8.2-src.tgz
tar -xvf flink-1.8.2-src.tgz
cd flink-1.8.2
mvn clean install -Pinclude-kinesis -DskipTests
```

### Deployment
- Generate FAT JAR using ShadowJar `./gradlew clean shadowJar`
- Copy the jar inside `build/libs/payment-digestor-4.0-SNAPSHOT-all.jar` over to an S3 bucket to be used by Kinesis Data Analytics

### TODO / FUTURE IMPROVEMENTS
- Add more unit test & integration, the test harness provided by flink has its own learning curve
- Watermarking make use of event timestamps currently using system time
- Integration serialization/deserialization better
