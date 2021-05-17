./gradlew clean build bootRun --args='--spring.profiles.active=local' # Kafka Traveler Microservices Demo: Accounts

## Accounts Service

Spring Boot/Kafka/Mongo Microservice, part of a set of microservices for this project. Services use Spring Kafka 2.1.6 to maintain eventually consistent data between their different `Customer` domain objects.

Originally code based on the post, [Spring Kafka - JSON Serializer Deserializer Example](https://www.codenotfound.com/spring-kafka-json-serializer-deserializer-example.html), from the [CodeNotFound.com](https://www.codenotfound.com/) Blog.

## Development

For [Kakfa](https://kafka.apache.org/), use [garystafford/kafka-docker](https://github.com/garystafford/kafka-docker) project, a clone of the [wurstmeister/kafka-docker](https://github.com/wurstmeister/kafka-docker) project. The `garystafford/kafka-docker` [local docker-compose file](https://github.com/garystafford/kafka-docker/blob/master/docker-compose-local.yml) builds a Kafka, Kafka Manager, ZooKeeper, MongoDB, Eureka Server, and Zuul.

## Commands

I develop and debug directly from JetBrains IntelliJ. The default Spring profile will start the three services on different ports.

```bash
./gradlew clean build bootRun --args='--spring.profiles.active=local'
```

Swagger UI: <http://localhost:8085/swagger-ui/index.html>

## Creating Sample Data

Create sample data for each service. Requires Kafka is running. Endpoints for Zuul, when using Docker Swarm/Stack, are different. See this [Python script](https://github.com/garystafford/storefront-kafka-docker/blob/master/refresh.py) for Zuul endpoints.

```bash
# accounts - create sample customer accounts
http http://localhost:8085/customers/sample

# orders - add sample orders to each customer
http http://localhost:8090/customers/sample/orders

# orders - send approved orders to fulfillment service
http http://localhost:8090/customers/sample/fulfill

# fulfillment - change fulfillment requests from approved to processing
http http://localhost:8095/fulfillments/sample/process

# fulfillment - change fulfillment requests from processing to shipping
http http://localhost:8095/fulfillments/sample/ship

# fulfillment - change fulfillment requests from processing to in transit
http http://localhost:8095/fulfillments/sample/in-transit

# fulfillment - change fulfillment requests from in transit to in received
http http://localhost:8095/fulfillments/sample/receive
```

## Container Infrastructure

$ docker container ls

```text
CONTAINER ID        IMAGE                                        COMMAND                  CREATED             STATUS              PORTS                                  NAMES
ccf0e9a0637d        garystafford/storefront-fulfillment:latest   "java -jar -Djava.se…"   11 minutes ago      Up 11 minutes       8080/tcp                               storefront_fulfillment.1.0mht01m6nk461q7mt1ey4zsjb
f8a4654183cb        hlebalbau/kafka-manager:latest               "/kafka-manager/bin/…"   11 minutes ago      Up 11 minutes                                              storefront_kafka_manager.1.so9h6c8veemrwlznj5zdk3sdw
fe6579d68846        garystafford/storefront-accounts:latest      "java -jar -Djava.se…"   11 minutes ago      Up 11 minutes       8080/tcp                               storefront_accounts.1.nafdn02w68nixyvmz7l46kzcq
2495802b640b        garystafford/storefront-eureka:latest        "java -jar -Djava.se…"   11 minutes ago      Up 11 minutes       8761/tcp                               storefront_eureka.1.x2tu8vg1dnizx61lwnudrnsml
5afe1e94162f        wurstmeister/kafka:latest                    "start-kafka.sh"         12 minutes ago      Up 11 minutes                                              storefront_kafka.1.n55qrkbqfueg1sgz0fb9h47qu
44a9d4dbdc4b        mongo:latest                                 "docker-entrypoint.s…"   12 minutes ago      Up 11 minutes       27017/tcp                              storefront_mongo.1.tfy3u2zi4bpcmb7372ihdjmbc
23be66801ebc        garystafford/storefront-orders:latest        "java -jar -Djava.se…"   12 minutes ago      Up 11 minutes       8080/tcp                               storefront_orders.1.bo5hfnqb9ijbfd7vp88hcs3vn
bbe4dbe00048        wurstmeister/zookeeper:latest                "/bin/sh -c '/usr/sb…"   12 minutes ago      Up 12 minutes       22/tcp, 2181/tcp, 2888/tcp, 3888/tcp   storefront_zookeeper.1.ipfwro51ob6fpls26bk14lvkt
98b8084f0162        garystafford/storefront-zuul:latest          "java -jar -Djava.se…"   12 minutes ago      Up 12 minutes       8761/tcp                               storefront_zuul.1.u4h4bxp01mcwetyoezk19ljzt
```

## Orders Customer Object in MongoDB

```text
docker exec -it $(docker ps | grep storefront_mongo | awk '{print $NF}') sh
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
  --from-beginning --topic accounts.customer.change \
  | jq
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
