# Koerber

This is the Koerber application.

After the startup, you can access to the swagger via
[this link](http://localhost:9020/koerber/v1/swaggers/swagger-ui/index.html).

The version control with the user name is not done. I've think on doing it with the envers library, and receiving the
user id via header or via authorization token.

I don't have create IT and splice tests to test the DB access and queries, as the controller parameters

My understandings regarding the challenge:
--

- When can not delete a filter if
    - Is in use on a branch
    - If a filter generate via branch. In that case the filter can only be deleted if a branch is deleted.
- When a branch is created
    - Is not possible to create a branch from another branch

Some available data
--

- 2 Users

| ID  | UUID | NAME  |
|-----|------|-------|
| 1   |  4413fb3e-d8f9-41ad-ace6-18816fda1e68    | user1 |
| 2   |   7a2678dc-9402-4532-88bf-ff58459130db   | user2 |

- 4 Filters (The last one is a filter branch)
    - 66b6049c-cf3d-4756-a350-e4170bbb9fd0
    - 45262bd8-3870-430e-b004-4cca08265894
    - f693c0e1-ef4c-4580-995c-96b136e299fb
    - 18f34399-decc-4955-98b8-4774856aeb31

- 1 Branch
    - 42bd549a-d066-4330-a6a5-e5855c79ccc1

Curls
--

- Filter
    - Get specific filter
      ```
      curl --location --request GET 'http://localhost:9020/koerber/v1/filters/40f816a9-04ad-41a8-91e7-859f57f69dda' \
      ```

    - Create a new filter
      ```
        curl --location --request POST 'http://localhost:9020/koerber/v1/filters/' \
        --header 'Content-Type: application/json' \
        --data-raw '{
        "userId":"4413fb3e-d8f9-41ad-ace6-18816fda1e68",
        "name": "This is a created filter",
        "data": "dt",
        "outputFilter": "of",
        "screenId": null
        }'
      ```

    - Update new filter
      ```
        curl --location --request PUT 'http://localhost:9020/koerber/v1/filters/40f816a9-04ad-41a8-91e7-859f57f69dda' \
        --header 'Content-Type: application/json' \
        --data-raw '{
        "id": 1,
        "version": 1,
        "userId":"4413fb3e-d8f9-41ad-ace6-18816fda1e68",
        "name": "This is a created filter",
        "data": "dt",
        "outputFilter": "of",
        "screenId": null
        }'
      ```

    - Delete a filter
      ```
       curl --location --request DELETE 'http://localhost:9020/koerber/v1/filters/40f816a9-04ad-41a8-91e7-859f57f69dda' \
      ```

    - Get all versions from a filter
      ```
        curl --location --request GET 'http://localhost:9020/koerber/v1/filters/66b6049c-cf3d-4756-a350-e4170bbb9fd0/versions' \
      ```

- Branch
    - Create branch
        ```
         curl --location --request POST 'http://localhost:9020/koerber/v1/branches' \
         --header 'Content-Type: application/json' \
         --data-raw '{
         "filterId": "45262bd8-3870-430e-b004-4cca08265894"
         }'
        ```

    - Merge branch
        ```
         curl --location --request POST 'http://localhost:9020/koerber/v1/branches/854503f6-a232-4e3b-8667-6d934129e101/merge' \
        ```

    - Delete branch
        ```
         curl --location --request DELETE 'http://localhost:9020/koerber/v1/branches/230880eb-32fa-4de4-94d2-1b6f0d9924f6' \
        ```
