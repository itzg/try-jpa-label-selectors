This is a little Spring Boot application where my goal was to use purely JPA queries to enable
queries of a labeled (key=value) resources in a SQL database.

Storing the key and value separately in the database and then querying them became impossible
(or at least very difficult). So, the solution cheats a little by textually combining the
key and value into a single string each using an `=` delimiter. A 
[collection membership predicate](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-member-of-collection-predicate)
is supported for element collections that are lists of strings, so that was the predicate
the queries are built around.

## Supported REST operations

### Bulk create resources

```http request
POST localhost:8080/resource/_batch
[
	{
		"name": "red apple",
		"labels": {
			"fruit": "apple",
			"color": "red"
		}
	},
	{
		"name": "green apple",
		"labels": {
			"fruit": "apple",
			"color": "green"
		}
	},
	{
		"name": "yellow apple",
		"labels": {
			"fruit": "apple",
			"color": "yellow"
		}
	},
	{
		"name": "green pear",
		"labels": {
			"fruit": "pear",
			"color": "green"
		}
	}
]
```

## Get all resources
```http request
GET localhost:8080/resource
```

## Delete all resources
```http request
DELETE localhost:8080/resource
```

## Query with label selector(s)

```http request
POST localhost:8080/_query/resource
{
	"labelSelectors": {
		"fruit": "apple"
	}
}
```

