## Demo Route Service


### Deploy Route Service

``` bash
$ ./mvnw clean package -DskipTests=true
$ cf push demo-route-service -p target/demo-route-service-0.0.1-SNAPSHOT.jar
$ cf logs demo-route-service
```

### Bind Route Service

```
$ cf cups demo-route-service -r https://demo-route-service.cfapps.io
$ cf bind-route-service cfapps.io demo-route-service -n your-app  
```

Access https://your-app.cfapps.io