var Swagger2Postman = require("swagger2-postman-generator");

require('bootprint')
  .load(require('bootprint-openapi'))
  .merge({})
  .build('http://localhost:9080/v2/api-docs', 'generated/html')
  .generate()
  .done(console.log)

Swagger2Postman
    .convertSwagger()
    .fromUrl("http://localhost:9080/v2/api-docs")
    .toPostmanCollectionFile("generated/postman_collection.json")