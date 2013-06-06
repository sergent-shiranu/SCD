// Exemplaire 
                "name": "1c15fad6-ed3b-4abc-9f7c-b7f6909bc97b-filterFull.jpg",
                "url": "http://files.parse.com/7c2e199b-5b4d-4ed1-9eff-5bcf976b3e44/1c15fad6-ed3b-4abc-9f7c-b7f6909bc97b-filterFull.jpg"
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  https://api.parse.com/1/classes/Livre | python -mjson.tool
  
  
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  https://api.parse.com/1/classes/Exemplaire | python -mjson.tool
  
  
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  https://api.parse.com/1/classes/Periodique | python -mjson.tool
  
  
  
  
// Livre BEC et BULATS

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"JJmrrAptGX"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/MXc1zTKpNA
  
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"JJmrrAptGX"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/aoOBhT3AD2
  

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"JJmrrAptGX"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/3ZvgeFGHbT
 
  
// Livre JQuery Mobile
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"7R7pSSt8ud"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/Fg35L2jJQZ

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"7R7pSSt8ud"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/jLBzW3Cck5

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"7R7pSSt8ud"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/Fg35L2jJQZ




 
// Livre Ajax
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"t9uCdRuS5S"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/xHWlu9AnJm
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"t9uCdRuS5S"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/mkCfhbCn59

  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"t9uCdRuS5S"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/FULcymLr9U



// Livre Android Wireless Application

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"7jy7vcLY0W"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/uanUNF7URE
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"7jy7vcLY0W"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/mIwRUlZyVr
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"7jy7vcLY0W"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/DEeI5BfhxG
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"7jy7vcLY0W"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/4aBFUlUi50
  
  
// Livre Business Benchmark

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"J8sK2Nw82v"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/0cJ7JmM1KX
  
  
  
// Livre Android forensic

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"IYl1fNRjdl"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/KbNwQLwW0e 
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"IYl1fNRjdl"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/x1dQ3AXAMd

  
  
// Livre 120 exos
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"bHtTmOLvgs"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/maTun7QePh

  
// Livre Essential Bulats 

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"XDlxVGMT11"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/9LJEuyiK0e

  
// Livre Achieve Bulats 

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"66zrfWffeT"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/C1HmWva0ex

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"66zrfWffeT"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/JvZomgXS6W


curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"66zrfWffeT"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/dQqNlwAJN3

  
  
// Livre Android 4
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"hU3ykOJsEr"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/bg6HY2Zv29

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"hU3ykOJsEr"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/c9EIVwpcun
  
// Livre Mecanique
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"Y5oegF4efN"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/QPBb0QzjH8

  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"Y5oegF4efN"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/FJhFUIekRa
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"Y5oegF4efN"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/ZVpviddUUS
  
  

// Remove Relation
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"exemplaire_de":{"__op":"RemoveRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"66zrfWffeT"}]}}' \
  https://api.parse.com/1/classes/Exemplaire/C1HmWva0ex
  
  
// Get all Exemplaires of a Livre  

curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -G \
  --data-urlencode 'where={"exemplaire_de":{"__type":"Pointer","className":"Livre","objectId":"Y5oegF4efN"}}' \
  https://api.parse.com/1/classes/Exemplaires
  
  
// Get Livre of an Exemplaire

curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -G \
  --data-urlencode 'where={"$relatedTo":{"object":{"__type":"Pointer","className":"Exemplaire","objectId":"ZVpviddUUS"},"key":"exemplaire_de"}}' \
  https://api.parse.com/1/classes/Livre
  
  
 

















// Livre BEC et BULATS

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"MXc1zTKpNA"}]}}' \
  https://api.parse.com/1/classes/Livre/JJmrrAptGX
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"aoOBhT3AD2"}]}}' \
  https://api.parse.com/1/classes/Livre/JJmrrAptGX
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"3ZvgeFGHbT"}]}}' \
  https://api.parse.com/1/classes/Livre/JJmrrAptGX
 

// // Livre JQuery Mobile

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"Fg35L2jJQZ"}]}}' \
  https://api.parse.com/1/classes/Livre/7R7pSSt8ud
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"jLBzW3Cck5"}]}}' \
  https://api.parse.com/1/classes/Livre/7R7pSSt8ud
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"av5mdgRiB5"}]}}' \
  https://api.parse.com/1/classes/Livre/7R7pSSt8ud
  
  
// Livre Ajax

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"xHWlu9AnJm"}]}}' \
  https://api.parse.com/1/classes/Livre/t9uCdRuS5S
  
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"mkCfhbCn59"}]}}' \
  https://api.parse.com/1/classes/Livre/t9uCdRuS5S
  

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"FULcymLr9U"}]}}' \
  https://api.parse.com/1/classes/Livre/t9uCdRuS5S
  

  
  
// Livre Android wireless Application

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"uanUNF7URE"}]}}' \
  https://api.parse.com/1/classes/Livre/7jy7vcLY0W


curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"mIwRUlZyVr"}]}}' \
  https://api.parse.com/1/classes/Livre/7jy7vcLY0W


curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"DEeI5BfhxG"}]}}' \
  https://api.parse.com/1/classes/Livre/7jy7vcLY0W


curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"4aBFUlUi50"}]}}' \
  https://api.parse.com/1/classes/Livre/7jy7vcLY0W  

  
  
  
// Livre Business Benchmark

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"0cJ7JmM1KX"}]}}' \
  https://api.parse.com/1/classes/Livre/J8sK2Nw82v

  
  
  
// Livre Android forensic  

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"x1dQ3AXAMd"}]}}' \
  https://api.parse.com/1/classes/Livre/IYl1fNRjdl
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"KbNwQLwW0e"}]}}' \
  https://api.parse.com/1/classes/Livre/IYl1fNRjdl
  
// Livre 120 exos

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"maTun7QePh"}]}}' \
  https://api.parse.com/1/classes/Livre/bHtTmOLvgs
  
  
// Livre Essential Bulats 

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"9LJEuyiK0e"}]}}' \
  https://api.parse.com/1/classes/Livre/XDlxVGMT11
  
  
// Livre Achieve Bulats

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"C1HmWva0ex"}]}}' \
  https://api.parse.com/1/classes/Livre/66zrfWffeT
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"JvZomgXS6W"}]}}' \
  https://api.parse.com/1/classes/Livre/66zrfWffeT
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"dQqNlwAJN3"}]}}' \
  https://api.parse.com/1/classes/Livre/66zrfWffeT

// Livre Android 4
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"bg6HY2Zv29"}]}}' \
  https://api.parse.com/1/classes/Livre/hU3ykOJsEr
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"c9EIVwpcun"}]}}' \
  https://api.parse.com/1/classes/Livre/hU3ykOJsEr
  
// Livre Mecanique
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"QPBb0QzjH8"}]}}' \
  https://api.parse.com/1/classes/Livre/Y5oegF4efN
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"FJhFUIekRa"}]}}' \
  https://api.parse.com/1/classes/Livre/Y5oegF4efN
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"ZVpviddUUS"}]}}' \
  https://api.parse.com/1/classes/Livre/Y5oegF4efN

  
  
// Remove Relation
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -d '{"has":{"__op":"RemoveRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"C1HmWva0ex"}]}}' \
  https://api.parse.com/1/classes/Livre/66zrfWffeT
  
  
  
// Get parent of a child
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -G \
  --data-urlencode 'where={"has":{"__type":"Pointer","className":"Exemplaire","objectId":"C1HmWva0ex"}}' \
  https://api.parse.com/1/classes/Livre
  
// Get child of a parent
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -G \
  --data-urlencode 'where={"$relatedTo":{"object":{"__type":"Pointer","className":"Livre","objectId":"66zrfWffeT"},"key":"has"}}' \
  https://api.parse.com/1/classes/Exemplaire
  
  
// Livre

curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  https://api.parse.com/1/classes/Livre | python -mjson.tool
 

 
 
 
 
// Chercher un livre 
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  -G \
  --data-urlencode 'where={"Titre" : { "$regex" : ".*[bB][Uu][Ll][Aa][Tt][Ss].*"} }' \
  https://api.parse.com/1/classes/Livre | python -mjson.tool
  
  
  
  
  
  
// User

// Login
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -G \
  --data-urlencode 'username=nguyenn2' \
  --data-urlencode 'password=123456789' \
  https://api.parse.com/1/login

"sessionToken": "sphllrmqm63gnx46z6mu9cqn7",  

curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "Content-Type: application/json" \
  --data-urlencode 'include=emprunter' \
  https://api.parse.com/1/users/kdBSL5IoyI

// Updating

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Session-Token: sphllrmqm63gnx46z6mu9cqn7" \
  -H "Content-Type: application/json" \
  -d '{"is_logging":"1"}' \
  https://api.parse.com/1/users/kdBSL5IoyI


// Emprunter un livre 
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Master-Key: BFEhYgNBo7ioJl1j9U3X7wyfDNe3rMwX1lDah1a9" \
  -H "Content-Type: application/json" \
  -d '{"emprunter":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Exemplaire","objectId":"dQqNlwAJN3"}]}}' \
  https://api.parse.com/1/users/kdBSL5IoyI
  
// Get livre prêté par user
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -G \
  --data-urlencode 'where={"$relatedTo":{"object":{"__type":"Pointer","className":"_User","objectId":"kdBSL5IoyI"},"key":"emprunter"}}' \
  https://api.parse.com/1/classes/Exemplaire
  
  
  
  
// Notifier court
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Master-Key: BFEhYgNBo7ioJl1j9U3X7wyfDNe3rMwX1lDah1a9" \
  -H "Content-Type: application/json" \
  -d '{"notifier_court":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"_User","objectId":"kdBSL5IoyI"}]}}' \
  https://api.parse.com/1/classes/Livre/7jy7vcLY0W

// Notifier Long
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Master-Key: BFEhYgNBo7ioJl1j9U3X7wyfDNe3rMwX1lDah1a9" \
  -H "Content-Type: application/json" \
  -d '{"notifier_long":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"_User","objectId":"kdBSL5IoyI"}]}}' \
  https://api.parse.com/1/classes/Livre/7jy7vcLY0W
  

  
// Panier Livre

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Master-Key: BFEhYgNBo7ioJl1j9U3X7wyfDNe3rMwX1lDah1a9" \
  -H "Content-Type: application/json" \
  -d '{"collecte_par":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"_User","objectId":"kdBSL5IoyI"},{"__type":"Pointer","className":"_User","objectId":"TGjNNsZKk7"}]}}' \
  https://api.parse.com/1/classes/Livre/Y5oegF4efN
  
  
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Master-Key: BFEhYgNBo7ioJl1j9U3X7wyfDNe3rMwX1lDah1a9" \
  -H "Content-Type: application/json" \
  -d '{"collecte_par":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"_User","objectId":"849H4TDKg7"}]}}' \
  https://api.parse.com/1/classes/Livre/Y5oegF4efN
  

curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Master-Key: BFEhYgNBo7ioJl1j9U3X7wyfDNe3rMwX1lDah1a9" \
  -H "Content-Type: application/json" \
  -d '{"collecte_par":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"_User","objectId":"kdBSL5IoyI"}]}}' \
  https://api.parse.com/1/classes/Livre/66zrfWffeT



// Emprunter un livre
curl -X PUT \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -H "X-Parse-Master-Key: BFEhYgNBo7ioJl1j9U3X7wyfDNe3rMwX1lDah1a9" \
  -H "Content-Type: application/json" \
  -d '{"ajouter_collection":{"__op":"AddRelation","objects":[{"__type":"Pointer","className":"Livre","objectId":"hU3ykOJsEr"}]}}' \
  https://api.parse.com/1/users/kdBSL5IoyI
  
// Get livre ajouté au panier d'un user
curl -X GET \
  -H "X-Parse-Application-Id: UhdjNYP0FdJoxZd1ZXFOdVx5JlJ0vQaWAPxwSlIx" \
  -H "X-Parse-REST-API-Key: mzeMNCCNXDWFYp0qSY0WydVEUVkYU3ilmxAO6uyx" \
  -G \
  --data-urlencode 'where={"$relatedTo":{"object":{"__type":"Pointer","className":"_User","objectId":"kdBSL5IoyI"},"key":"ajouter_collection"}}' \
  https://api.parse.com/1/classes/Livre
  


  

