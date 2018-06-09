# Kafka Traveler Microservices Demo: Accounts

## Accounts Service

Spring Boot/Kafka/Mongo Microservice, part of a set of microservices for this project. Services use Spring Kafka 2.1.6 to maintain eventually consistent data between their different `Customer` domain objects.

Originally code based on the post, [Spring Kafka - JSON Serializer Deserializer Example](https://www.codenotfound.com/spring-kafka-json-serializer-deserializer-example.html), from the [CodeNotFound.com](https://www.codenotfound.com/) Blog. Original business domain idea based on the post, [Distributed Sagas for Microservices](https://dzone.com/articles/distributed-sagas-for-microservices), on [DZone](https://dzone.com/).

## Development

For [Kakfa](https://kafka.apache.org/), I use my [garystafford/kafka-docker](https://github.com/garystafford/kafka-docker) project, a clone of the [wurstmeister/kafka-docker](https://github.com/wurstmeister/kafka-docker) project. The `garystafford/kafka-docker` [local docker-compose file](https://github.com/garystafford/kafka-docker/blob/master/docker-compose-local.yml) builds a Kafka, Kafka Manager, ZooKeeper, and MongoDB.

## Commands

I develop and debug directly from JetBrains IntelliJ. The default Spring profile will start the three services on different ports.

```bash
./gradlew clean build bootRun
```

## Creating Sample Data

Create sample data for each service. Requires Kafka is running.

```bash
# accounts - create sample customer accounts
http http://localhost:8085/customers/sample

# orders - add sample orders to each customer
http http://localhost:8090/customers/sample/orders

# orders - send approved orders to fulfillment service
http http://localhost:8090/customers/fulfill

# fulfillment - change fulfillment requests from approved to processing
http http://localhost:8095/fulfillment/sample/process

# fulfillment - change fulfillment requests from processing to shipping
http http://localhost:8095/fulfillment/sample/ship

# fulfillment - change fulfillment requests from processing to in transit
http http://localhost:8095/fulfillment/sample/in-transit

# fulfillment - change fulfillment requests from in transit to in received
http http://localhost:8095/fulfillment/sample/receive
```

## Container Infrastructure

```text
CONTAINER ID        IMAGE                            COMMAND                  CREATED             STATUS              PORTS                                                NAMES
df8914058cbb        hlebalbau/kafka-manager:latest   "/kafka-manager/bin/…"   4 hours ago         Up 4 hours          0.0.0.0:9000->9000/tcp                               kafka-docker_kafka_manager_1
5cd8f61330e0        wurstmeister/kafka:latest        "start-kafka.sh"         4 hours ago         Up 4 hours          0.0.0.0:9092->9092/tcp                               kafka-docker_kafka_1
497901621c7d        mongo:latest                     "docker-entrypoint.s…"   4 hours ago         Up 4 hours          0.0.0.0:27017->27017/tcp                             kafka-docker_mongo_1
9079612e36ad        wurstmeister/zookeeper:latest    "/bin/sh -c '/usr/sb…"   4 hours ago         Up 4 hours          22/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp   kafka-docker_zookeeper_1
```

## Orders Customer Object in MongoDB

```text
docker exec -it kafka-docker_mongo_1 sh
mongo
db.customer.accounts.find().pretty()
db.customer.orders.remove({})
```

```bson
{
	"_id" : ObjectId("5b18661ebe417602a48132ed"),
	"name" : {
		"title" : "Ms.",
		"firstName" : "Susan",
		"lastName" : "Blackstone"
	},
	"contact" : {
		"primaryPhone" : "433-544-6555",
		"secondaryPhone" : "223-445-6767",
		"email" : "susan.m.blackstone@emailisus.com"
	},
	"addresses" : [
		{
			"type" : "BILLING",
			"description" : "My CC billing address",
			"address1" : "33 Oak Avenue",
			"city" : "Nowhere",
			"state" : "VT",
			"postalCode" : "444556-9090"
		},
		{
			"type" : "SHIPPING",
			"description" : "Home Sweet Home",
			"address1" : "33 Oak Avenue",
			"city" : "Nowhere",
			"state" : "VT",
			"postalCode" : "444556-9090"
		}
	],
	"creditCards" : [
		{
			"type" : "PRIMARY",
			"description" : "Master Card",
			"number" : "4545-5656-7878-9090",
			"expiration" : "4/19",
			"nameOnCard" : "Susan M. Blackstone"
		}
	],
	"_class" : "com.storefront.model.Customer"
}
```

## Current Results

Output from application, on the `accounts.customer.change` topic

```text
2018-06-03 03:17:36.510  INFO [-,aee18dd362da06b4,aee18dd362da06b4,false] 12 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version : 1.0.1
2018-06-03 03:17:36.510  INFO [-,aee18dd362da06b4,aee18dd362da06b4,false] 12 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId : c0518aa65f25317e
2018-06-03 03:17:37.163  INFO [-,aee18dd362da06b4,aee18dd362da06b4,false] 12 --- [nio-8080-exec-1] c.storefront.handler.AfterSaveListener   : event='org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent[source=Customer(id=5b135dd0be4176000cf30283, name=Name(title=Ms., firstName=Mary, middleName=null, lastName=Smith, suffix=null), contact=Contact(primaryPhone=456-789-0001, secondaryPhone=456-222-1111, email=marysmith@yougotmail.com), addresses=[Address(type=BILLING, description=My CC billing address, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677), Address(type=SHIPPING, description=Home Sweet Home, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677)], creditCards=[CreditCard(type=PRIMARY, description=VISA, number=4545-6767-8989-0000, expiration=7/21, nameOnCard=Mary Smith)])]'
2018-06-03 03:17:37.163  INFO [-,aee18dd362da06b4,aee18dd362da06b4,false] 12 --- [nio-8080-exec-1] com.storefront.kafka.Sender              : sending payload='Customer(id=5b135dd0be4176000cf30283, name=Name(title=Ms., firstName=Mary, middleName=null, lastName=Smith, suffix=null), contact=Contact(primaryPhone=456-789-0001, secondaryPhone=456-222-1111, email=marysmith@yougotmail.com), addresses=[Address(type=BILLING, description=My CC billing address, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677), Address(type=SHIPPING, description=Home Sweet Home, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677)], creditCards=[CreditCard(type=PRIMARY, description=VISA, number=4545-6767-8989-0000, expiration=7/21, nameOnCard=Mary Smith)])' to topic='accounts.customer.change'
2018-06-03 03:17:37.166  INFO [-,aee18dd362da06b4,aee18dd362da06b4,false] 12 --- [nio-8080-exec-1] c.storefront.handler.AfterSaveListener   : event='org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent[source=Customer(id=5b135dd0be4176000cf30284, name=Name(title=Ms., firstName=Susan, middleName=null, lastName=Blackstone, suffix=null), contact=Contact(primaryPhone=433-544-6555, secondaryPhone=223-445-6767, email=susan.m.blackstone@emailisus.com), addresses=[Address(type=BILLING, description=My CC billing address, address1=33 Oak Avenue, address2=null, city=Nowhere, state=VT, postalCode=444556-9090), Address(type=SHIPPING, description=Home Sweet Home, address1=33 Oak Avenue, address2=null, city=Nowhere, state=VT, postalCode=444556-9090)], creditCards=[CreditCard(type=PRIMARY, description=Master Card, number=4545-5656-7878-9090, expiration=4/19, nameOnCard=Susan M. Blackstone)])]'
2018-06-03 03:17:37.166  INFO [-,aee18dd362da06b4,aee18dd362da06b4,false] 12 --- [nio-8080-exec-1] com.storefront.kafka.Sender              : sending payload='Customer(id=5b135dd0be4176000cf30284, name=Name(title=Ms., firstName=Susan, middleName=null, lastName=Blackstone, suffix=null), contact=Contact(primaryPhone=433-544-6555, secondaryPhone=223-445-6767, email=susan.m.blackstone@emailisus.com), addresses=[Address(type=BILLING, description=My CC billing address, address1=33 Oak Avenue, address2=null, city=Nowhere, state=VT, postalCode=444556-9090), Address(type=SHIPPING, description=Home Sweet Home, address1=33 Oak Avenue, address2=null, city=Nowhere, state=VT, postalCode=444556-9090)], creditCards=[CreditCard(type=PRIMARY, description=Master Card, number=4545-5656-7878-9090, expiration=4/19, nameOnCard=Susan M. Blackstone)])' to topic='accounts.customer.change'
```

Output from Kafka container using the following command.

```bash
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --from-beginning --topic accounts.customer.change
```

Kafka Consumer Output

```text
{"id":"5b1be010a8d05620a3d7efe8","name":{"title":"Mr.","firstName":"John","middleName":"S.","lastName":"Doe","suffix":"Jr."},"contact":{"primaryPhone":"555-666-7777","secondaryPhone":"555-444-9898","email":"john.doe@internet.com"},"addresses":[{"type":"BILLING","description":"My cc billing address","address1":"123 Oak Street","address2":null,"city":"Sunrise","state":"CA","postalCode":"12345-6789"},{"type":"SHIPPING","description":"My home address","address1":"123 Oak Street","address2":null,"city":"Sunrise","state":"CA","postalCode":"12345-6789"}]}
{"id":"5b1be010a8d05620a3d7efe9","name":{"title":"Ms.","firstName":"Mary","middleName":null,"lastName":"Smith","suffix":null},"contact":{"primaryPhone":"456-789-0001","secondaryPhone":"456-222-1111","email":"marysmith@yougotmail.com"},"addresses":[{"type":"BILLING","description":"My CC billing address","address1":"1234 Main Street","address2":null,"city":"Anywhere","state":"NY","postalCode":"45455-66677"},{"type":"SHIPPING","description":"Home Sweet Home","address1":"1234 Main Street","address2":null,"city":"Anywhere","state":"NY","postalCode":"45455-66677"}]}
{"id":"5b1be010a8d05620a3d7efea","name":{"title":"Ms.","firstName":"Susan","middleName":null,"lastName":"Blackstone","suffix":null},"contact":{"primaryPhone":"433-544-6555","secondaryPhone":"223-445-6767","email":"susan.m.blackstone@emailisus.com"},"addresses":[{"type":"BILLING","description":"My CC billing address","address1":"33 Oak Avenue","address2":null,"city":"Nowhere","state":"VT","postalCode":"444556-9090"},{"type":"SHIPPING","description":"Home Sweet Home","address1":"33 Oak Avenue","address2":null,"city":"Nowhere","state":"VT","postalCode":"444556-9090"}]}
```

To manually create `accounts.customer.change` topic

```bash
kafka-topics.sh --create \
  --zookeeper zookeeper:2181 \
  --replication-factor 1 --partitions 1 \
  --topic accounts.customer.change
```

Clear messages from `accounts.customer.change` topic

Delete the topic from the Kafka Manager UI, or from the commandline:

```bash
kafka-configs.sh --zookeeper zookeeper:2181 \
  --alter --entity-type topics --entity-name accounts.customer.change \
  --add-config retention.ms=1000

# wait ~ 2-3 minutes to clear...check if clear
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --from-beginning --topic accounts.customer.change

kafka-configs.sh --zookeeper zookeeper:2181 \
  --alter --entity-type topics --entity-name accounts.customer.change \
  --delete-config retention.ms
```

## References

-   [Spring Kafka – Consumer and Producer Example](https://memorynotfound.com/spring-kafka-consume-producer-example/)
-   [Spring Kafka - JSON Serializer Deserializer Example](https://www.codenotfound.com/spring-kafka-json-serializer-deserializer-example.html)
-   [Spring for Apache Kafka: 2.1.6.RELEASE](https://docs.spring.io/spring-kafka/reference/html/index.html)
-   [Spring Data MongoDB - Reference Documentation](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
