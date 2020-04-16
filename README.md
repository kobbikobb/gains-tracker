
# Run it
./mvnw spring-boot:run

# Actuator and health
http://localhost:8080/actuator

# Guides
https://spring.io/guides/gs/spring-boot/

# Docker
- docker build -f Dockerfile -t gains-tracker .
- docker run -p 8089:8088 gains-tracker

# Tables

create volume to store data
docker create -v /var/lib/postgresql/data --name PostgresData alpine

create database
docker run -p 5432:5433 --name postgres -e POSTGRES_PASSWORD=admin -d --volumes-from PostgresData postgres

actions
id name       unit
1  Handstands Seconds
2  Push ups   Count
3  Run        Km

actionLogs
actionId    value    date
1           20       2009-01-01
2           30       2009-01-01
3           2.6      2009-01-01

drop table actions
drop table action_logs

CREATE TABLE actions (
        id         SERIAL,
        Name       TEXT,
        Unit       TEXT,
        PRIMARY KEY (id)
);

CREATE TABLE action_logs (
        id         SERIAL,
        action_id   SERIAL,
        value      NUMERIC (10, 2),
        date       date,
        PRIMARY KEY (id),
        FOREIGN KEY (action_id) REFERENCES actions (id)
);

Insert Into actions (name, unit) Values ('Handstands', 'Seconds')
Insert Into action_logs (action_id, value, date) Values (1, 30.5, '2020-04-15')

Select * From actions Join action_logs on action_id = action_logs.action_id