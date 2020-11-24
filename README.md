## Demo for JConf 2020

This is an example for JConf Central America 2020, the goal is to create a REST API
that returns some events under a category, these events has a description and latitude/longitude to present in maps


### Requirements

- [x] JDK 1.8.x
- [x] Maven
- [x] Database for mysql, you can use Docker  with the folder `backing-services`
- [x] docker/docker-compose (optional)

#### Startup database
```
cd backing-services

docker-compose up -d

# credentials
    # user=root
    # password=password
    # database=back
```

#### Run migrations
The use of flyway is desirable
```
flyway -configFiles=db/conf/flyway.conf migrate

# to clean the database
flyway -configFiles=db/conf/flyway.conf to clean
```


#### Create the package / run
```
mvn clean package

java -jar target/demo-0.0.1-SNAPSHOT.jar 
```
The application is running in port `8090`, to change check: `src/main/resources/application.properties`

#### Manual testing
Request Categories 

`curl localhost:8090/api/categories | jq .`

Result 

```
[
  {
    "id": 1,
    "name": "Corte de agua",
    "createdAt": "2020-11-23T19:25:05",
    "active": true
  },
  {
    "id": 2,
    "name": "Corte de luz;",
    "createdAt": "2020-11-23T19:25:05",
    "active": true
  }
]
```

Request Events 

`curl localhost:8090/api/events | jq .`

Result 

```
[
  {
    "id": 1,
    "name": "Mantenimiento",
    "address": "Zona 12",
    "description": "",
    "latitude": "14.622784025555436",
    "longitude": "-90.55425550456252",
    "category": {
      "id": 1,
      "name": "Corte de agua",
      "createdAt": "2020-11-23T19:25:05",
      "active": true
    }
  }
]
```

