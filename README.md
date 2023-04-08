
<p align="center">
<h1>Relic</h1>
🚀 A quick, lightweight and simple ORM (Object Relational Mapper) for Java.
</p>

<h2>Quick example</h2>
<h3>Installing with Maven</h3>

```xml

<dependency>
    <groupId>io.github.viiictorxd</groupId>
    <artifactId>relic</artifactId>
    <version>1.0.0</version>
</dependency>
```
<h2>Samples</h2>

```java
// Defines a custom name for the table, if the annotation is not present, the table name will be the same as the class name.
@Table
```

```java
// A field needs this annotation to be recognized when using the "find" method.
@Id
```

```java
// For fields to be recognized by Relic's methods, this annotation is necessary.
@Column
```

Here are some examples:
```java
import io.github.viiictorxd.relic.annotation.Column;
import io.github.viiictorxd.relic.annotation.Id;
import io.github.viiictorxd.relic.annotation.Table;

@Table(name = "my_custom_table_name")
public class MyObject {

    @Id
    @Column
    private String name;

    @Column
    private int age;

    @Column(name = "city_name")
    private String city;
}
```

Creating a table
```java
Relic relic = Relic.inst();

Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

myObjectProcedure.createTable();
```

Deleting an object
```java
Relic relic = Relic.inst();

Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

MyObject myObject = new MyObject("Victor", 25, "RJ");

myObjectProcedure.delete(myObject);
```

Inserting an object
```java
Relic relic = Relic.inst();

Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

MyObject myObject = new MyObject("Almir", 38, "MG");

myObjectProcedure.insert(myObject);
```

Selecting all objects
```java
Relic relic = Relic.inst();

Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

List<MyObject> all = myObjectProcedure.findAll();

all.forEach(System.out::println);
```

Selecting one by ID
```java
Relic relic = Relic.inst();

Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

MyObject myObject = myObjectProcedure.find("Victor");

System.out.println(myObject.toString());
```

Updating an object
```java
Relic relic = Relic.inst();

Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

MyObject myObject = new MyObject("Victor", 30, "RJ");

myObjectProcedure.update(myObject);
```

<h2>Issues</h2>
Please make sure to read the Issue Reporting Checklist before opening an issue. Issues not conforming to the guidelines may be closed immediately.

<h2>Contributors</h2>
<table>
  <tr>
    <td align="center"><a href="https://github.com/ViiictorXD">
<img src="https://avatars3.githubusercontent.com/u/38568440?v=4" width="100px;" alt=""/><br /><sub><b>ViiictorXD</b></sub></a><br /><a href="https://github.com/ViiictorXD/CombatChanger/commits?author=ViiictorXD" title="Code">💻</a></td>
  </tr>
</table>
