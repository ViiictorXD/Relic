package io.github.viiictorxd.relic.object;

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

    public MyObject(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "MyObject{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", city='" + city + '\'' +
          '}';
    }
}
