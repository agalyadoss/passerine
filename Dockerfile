 From durai145/passerine.2021.12.01-001
ADD . /app
WORKDIR /app/
ENTRYPOINT ["java"] 
CMD [ "-cp", "/app/target/passerine-1.0-SNAPSHOT-jar-with-dependencies.jar", "com.heaerie.passerine.service.Search", "1.0.0.0", "126.255.255.255"]
