FROM mongo

COPY posts.json /posts.json
COPY profiles.json /profiles.json
COPY users.json /users.json

CMD mongoimport --host mongo --db devconnector --collection posts --type json --file /posts.json --jsonArray && \
    mongoimport --host mongo --db devconnector --collection users --type json --file /users.json --jsonArray && \
    mongoimport --host mongo --db devconnector --collection profiles --type json --file /profiles.json --jsonArray
