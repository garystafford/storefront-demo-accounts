# Kafka Traveler Microservices Demo: Accounts

## Accounts Service

Spring Boot/Kafka/Mongo Microservice, part of a set of microservices for this project. Services use Spring Kafka 2.1.6 to maintain eventually consistent data between their different `Customer` domain objects.

Originally code based on the post, [Spring Kafka - JSON Serializer Deserializer Example](https://www.codenotfound.com/spring-kafka-json-serializer-deserializer-example.html), from the [CodeNotFound.com](https://www.codenotfound.com/) Blog. Original business domain idea based on the post, [Distributed Sagas for Microservices](https://dzone.com/articles/distributed-sagas-for-microservices), on [DZone](https://dzone.com/).

## Development

For [Kakfa](https://kafka.apache.org/), I use my [garystafford/kafka-docker](https://github.com/garystafford/kafka-docker) project, a clone of the [wurstmeister/kafka-docker](https://github.com/wurstmeister/kafka-docker) project. The `garystafford/kafka-docker` [local docker-compose file](https://github.com/garystafford/kafka-docker/blob/master/docker-compose-local.yml) builds a Kafka, ZooKeeper, MongoDB, and Alpine Linux OpenJDK container.

## Commands

I debug directly from JetBrains IntelliJ. For testing the application in development, I build the jar, copy it to Alpine Linux OpenJDK `testapp` container, and run it. If testing more than one service in the same testapp container, make sure ports don't collide. Start services on different ports.

```bash
# start container if stopped
docker start kafka-docker_testapp_1

# build
./gradlew clean build

# copy
docker cp build/libs/accounts-1.0.0.jar kafka-docker_testapp_1:/accounts-1.0.0.jar
docker exec -it kafka-docker_testapp_1 sh

# install curl
apk update && apk add curl

# start with 'dev' profile
java -jar accounts-1.0.0.jar \
  --spring.profiles.active=dev
```

## Creating Sample Data

Create sample customers with an order history.
```bash
# create sample accounts customers
curl http://localhost:8080/customers/sample

# create sample orders products
curl http://localhost:8890/products/sample
# add sample order history to orders customers
# (received from kafka accounts.customer.save topic)

curl http://localhost:8890/customers/sample

```

## Container Infrastructure

```text
CONTAINER ID        IMAGE                           COMMAND                  CREATED             STATUS              PORTS                                                NAMES
f11b56e51d57        openjdk:8u151-jdk-alpine3.7     "sleep 6000"             4 seconds ago       Up 3 seconds                                                             kafka-docker_testapp_1
ede27d4c993b        mongo:latest                    "docker-entrypoint.s…"   21 hours ago        Up 21 hours         0.0.0.0:27017->27017/tcp                             kafka-docker_mongo_1
fde71dcb89be        wurstmeister/kafka:latest       "start-kafka.sh"         21 hours ago        Up 21 hours         0.0.0.0:9092->9092/tcp                               kafka-docker_kafka_1
538397f51320        wurstmeister/zookeeper:latest   "/bin/sh -c '/usr/sb…"   21 hours ago        Up 21 hours         22/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp   kafka-docker_zookeeper_1
```

## Orders Customer Object in MongoDB

`db.customer.find().pretty();`

```bson
{
	"_id" : ObjectId("5b0c54e2be41760051d00383"),
	"name" : {
		"title" : "Mr.",
		"firstName" : "John",
		"middleName" : "S.",
		"lastName" : "Doe",
		"suffix" : "Jr."
	},
	"contact" : {
		"primaryPhone" : "555-666-7777",
		"secondaryPhone" : "555-444-9898",
		"email" : "john.doe@internet.com"
	},
	"addresses" : [
		{
			"type" : "BILLING",
			"description" : "My cc billing address",
			"address1" : "123 Oak Street",
			"city" : "Sunrise",
			"state" : "CA",
			"postalCode" : "12345-6789"
		},
		{
			"type" : "SHIPPING",
			"description" : "My home address",
			"address1" : "123 Oak Street",
			"city" : "Sunrise",
			"state" : "CA",
			"postalCode" : "12345-6789"
		}
	],
	"creditCards" : [
		{
			"type" : "PRIMARY",
			"description" : "VISA",
			"number" : "1234-6789-0000-0000",
			"expiration" : "6/19",
			"nameOnCard" : "John S. Doe"
		},
		{
			"type" : "ALTERNATE",
			"description" : "Corporate American Express",
			"number" : "9999-8888-7777-6666",
			"expiration" : "3/20",
			"nameOnCard" : "John Doe"
		}
	],
	"credentials" : {
		"username" : "johndoe37",
		"password" : "skd837#$hfh485&"
	},
	"_class" : "com.storefront.model.Customer"
}
```

## Current Results

Output from application, on the `accounts.customer.save` topic

```text
2018-05-29 00:43:28.533  INFO 47 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version : 1.0.1

2018-05-29 00:43:28.534  INFO 47 --- [nio-8080-exec-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId : c0518aa65f25317e

2018-05-29 00:43:28.948  INFO 47 --- [nio-8080-exec-1] c.storefront.handler.AfterSaveListener   : event='org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent[source=Customer(id=5b0ca230be4176002f6199a0, name=Name(title=Ms., firstName=Mary, middleName=null, lastName=Smith, suffix=null), contact=Contact(primaryPhone=456-789-0001, secondaryPhone=456-222-1111, email=marysmith@yougotmail.com), addresses=[Address(type=BILLING, description=My CC billing address, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677), Address(type=SHIPPING, description=Home Sweet Home, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677)], creditCards=[CreditCard(type=PRIMARY, description=VISA, number=4545-6767-8989-0000, expiration=7/21, nameOnCard=Mary Smith)], credentials=Credentials(username=msmith445, password=S*$475hf&*dddFFG3))]'

2018-05-29 00:43:28.950  INFO 47 --- [nio-8080-exec-1] com.storefront.kafka.Sender              : sending payload='Customer(id=5b0ca230be4176002f6199a0, name=Name(title=Ms., firstName=Mary, middleName=null, lastName=Smith, suffix=null), contact=Contact(primaryPhone=456-789-0001, secondaryPhone=456-222-1111, email=marysmith@yougotmail.com), addresses=[Address(type=BILLING, description=My CC billing address, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677), Address(type=SHIPPING, description=Home Sweet Home, address1=1234 Main Street, address2=null, city=Anywhere, state=NY, postalCode=45455-66677)], creditCards=[CreditCard(type=PRIMARY, description=VISA, number=4545-6767-8989-0000, expiration=7/21, nameOnCard=Mary Smith)], credentials=Credentials(username=msmith445, password=S*$475hf&*dddFFG3))' to topic='accounts.customer.save'
```

Output from Kafka container using the following command.

```bash
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --from-beginning --topic accounts.customer.save
```

Kafka Consumer Output

```text
{"id":"5b0ca230be4176002f61999f","name":{"title":"Mr.","firstName":"John","middleName":"S.","lastName":"Doe","suffix":"Jr."},"contact":{"primaryPhone":"555-666-7777","secondaryPhone":"555-444-9898","email":"john.doe@internet.com"},"addresses":[{"type":"BILLING","description":"My cc billing address","address1":"123 Oak Street","address2":null,"city":"Sunrise","state":"CA","postalCode":"12345-6789"},{"type":"SHIPPING","description":"My home address","address1":"123 Oak Street","address2":null,"city":"Sunrise","state":"CA","postalCode":"12345-6789"}],"creditCards":[{"type":"PRIMARY","description":"VISA","number":"1234-6789-0000-0000","expiration":"6/19","nameOnCard":"John S. Doe"},{"type":"ALTERNATE","description":"Corporate American Express","number":"9999-8888-7777-6666","expiration":"3/20","nameOnCard":"John Doe"}],"credentials":{"username":"johndoe37","password":"skd837#$hfh485&"}}

{"id":"5b0ca230be4176002f6199a0","name":{"title":"Ms.","firstName":"Mary","middleName":null,"lastName":"Smith","suffix":null},"contact":{"primaryPhone":"456-789-0001","secondaryPhone":"456-222-1111","email":"marysmith@yougotmail.com"},"addresses":[{"type":"BILLING","description":"My CC billing address","address1":"1234 Main Street","address2":null,"city":"Anywhere","state":"NY","postalCode":"45455-66677"},{"type":"SHIPPING","description":"Home Sweet Home","address1":"1234 Main Street","address2":null,"city":"Anywhere","state":"NY","postalCode":"45455-66677"}],"creditCards":[{"type":"PRIMARY","description":"VISA","number":"4545-6767-8989-0000","expiration":"7/21","nameOnCard":"Mary Smith"}],"credentials":{"username":"msmith445","password":"S*$475hf&*dddFFG3"}}
```

Create `accounts.customer.save` topic

```bash
kafka-topics.sh --create \
  --zookeeper zookeeper:2181 \
  --replication-factor 1 --partitions 1 \
  --topic accounts.customer.save
```

Clear messages from `accounts.customer.save` topic

```bash
kafka-configs.sh --zookeeper zookeeper:2181 \
  --alter --entity-type topics --entity-name accounts.customer.save \
  --add-config retention.ms=1000

# wait ~ 2-3 minutes to clear...check if clear
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --from-beginning --topic accounts.customer.save

kafka-configs.sh --zookeeper zookeeper:2181 \
  --alter --entity-type topics --entity-name accounts.customer.save \
  --delete-config retention.ms
```


## References

-   [Spring Kafka – Consumer and Producer Example](https://memorynotfound.com/spring-kafka-consume-producer-example/)
-   [Spring Kafka - JSON Serializer Deserializer Example
    ](https://www.codenotfound.com/spring-kafka-json-serializer-deserializer-example.html)
-   [Spring for Apache Kafka: 2.1.6.RELEASE](https://docs.spring.io/spring-kafka/reference/html/index.html)
