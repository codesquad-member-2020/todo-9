# TODO Project Team 9 BackEnd

## Local

- h2 database 사용하여 빠른 개발

## Deploy

- MySQL Database 사용

## Swagger 사용

http://15.165.21.99:8080/swagger-ui.html

## API docs

`GET` 요청으로 `/board` 요청시

```json
{
  "id": 1,
  "name": "To Do 프로젝트",
  "columns": [
    {
      "id": 1,
      "name": "todo",
      "createdAt": "2020-04-07 12:20:25",
      "updatedAt": "2020-04-07 12:20:25",
      "archivedAt": null,
      "order": 1,
      "cards": [
        {
          "id": 1,
          "contents": "안녕하세요~~",
          "createdAt": "2020-04-07 12:20:25",
          "updatedAt": "2020-04-07 12:20:25",
          "archivedAt": null,
          "beforeCardId": null,
          "afterCardId": 2,
          "createdUserId": 1,
          "updatedUserId": 1,
          "archived": false
        },
        {
          "id": 2,
          "contents": "asdf",
          "createdAt": "2020-04-07 12:20:25",
          "updatedAt": "2020-04-07 12:20:25",
          "archivedAt": null,
          "beforeCardId": 1,
          "afterCardId": null,
          "createdUserId": 1,
          "updatedUserId": 1,
          "archived": false
        }
      ],
      "archived": false
    },
    {
      "id": 2,
      "name": "doing",
      "createdAt": "2020-04-07 12:20:25",
      "updatedAt": "2020-04-07 12:20:25",
      "archivedAt": null,
      "order": 2,
      "cards": null,
      "archived": false
    },
    {
      "id": 3,
      "name": "done",
      "createdAt": "2020-04-07 12:20:25",
      "updatedAt": "2020-04-07 12:20:25",
      "archivedAt": null,
      "order": 3,
      "cards": null,
      "archived": false
    }
  ],
  "logs": [
    {
      "id": 1,
      "action": "create",
      "beforeCard": null,
      "afterCard": null,
      "fromColumn": null,
      "toColumn": 1,
      "actionedAt": "2020-04-07 12:20:25",
      "boardId": 1
    },
    {
      "id": 2,
      "action": "create",
      "beforeCard": null,
      "afterCard": null,
      "fromColumn": null,
      "toColumn": 2,
      "actionedAt": "2020-04-07 12:20:25",
      "boardId": 1
    },
    {
      "id": 3,
      "action": "create",
      "beforeCard": null,
      "afterCard": null,
      "fromColumn": null,
      "toColumn": 3,
      "actionedAt": "2020-04-07 12:20:25",
      "boardId": 1
    },
    {
      "id": 4,
      "action": "create",
      "beforeCard": null,
      "afterCard": 1,
      "fromColumn": 1,
      "toColumn": 1,
      "actionedAt": "2020-04-07 12:20:25",
      "boardId": 1
    },
    {
      "id": 5,
      "action": "create",
      "beforeCard": null,
      "afterCard": 2,
      "fromColumn": 1,
      "toColumn": 1,
      "actionedAt": "2020-04-07 12:20:25",
      "boardId": 1
    }
  ]
}
```
를 반환합니다.
