## Правильно настроен Maven-проект, тесты проходят</summary>
  
  Репозиторий должен быть папкой вашего мавен-проекта. Обратите внимание, что репозиторием не должна быть папка в которой лежит папка мавен-проекта, он сам должен быть папкой проекта. В нём должны быть соответствующие файлы и папки - `pom.xml`, `src` и др.
  
  Не забудьте создать .gitignore-файл в корне проекта и добавить туда в игнорирование автогенерируемую папку `target`.
  
  Общая схема вашего `pom.xml`-файла:
  
  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>ru.netology</groupId>
    <artifactId>НАЗВАНИЕ-ВАШЕГО-ПРОЕКТА-БЕЗ-ПРОБЕЛОВ</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>


    <dependencies>
        <dependency>
            ...
        </dependency>
        ...
    </dependencies>


    <build>
        <plugins>
            <plugin>
              ...
            </plugin>

            <plugin>
              ...
              <executions>
                <execution>
                  ...
                </execution>
                ...
              </executions>
            </plugin>
            ...
        </plugins>
    </build>

</project>
  ```
  
  #### JUnit
  Обратите внимание что у артефакта нет `-api` на конце. Если у вас автоматически добавилась зависимость вида `<artifactId>junit-jupiter-api</artifactId>`, то лучше поменять артефакт на тот что ниже, иначе будут сюрпризы в работе.

  ```xml
          <dependency>
              <groupId>org.junit.jupiter</groupId>
              <artifactId>junit-jupiter</artifactId>
              <version>5.7.0</version>
              <scope>test</scope>
          </dependency>
  ```

  #### Surefire
  Без этого плагина тесты могут мавеном не запускаться, хоть в идее через кнопки они и будут проходить. Чтобы лишний раз убедиться, что всё работает, нажмите `Ctrl+Ctrl` и затем `mvn clean test`.
  
  ```xml
              <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-surefire-plugin</artifactId>
                  <version>2.22.2</version>
                  <configuration>
                      <failIfNoTests>true</failIfNoTests>
                  </configuration>
              </plugin>
  ```
  
  ---
  
## Отформатирован код
  
  Кроме правил, нарушение которых приводит к ошибкам компиляции, есть ещё и [правила форматирования кода](https://google.github.io/styleguide/javaguide.html), соблюдение которых обязательно при напиании программ.
  
  С большинством проблем может справиться автоформатирование в идее. Для этого выберите `Code -> Reformat code` в меню или используйте горячие сочетания клавиш (в меню будет показано актуальное сочетание для вашей операционной системы). Так, идея поправит неправильные отступы, пробелы и некоторые другие ошибки. Следите, чтобы у `if-else`, `for`, `while` всегда были `{}`.
  
  Проблемы с именованием сущностей нужно решать самим. Так, все ячейки кроме `final`-констант и методы должны писаться с маленькой буквы [камелкейсом](https://ru.wikipedia.org/wiki/CamelCase) с **маленькой** буквы, а классы и интерфейсы - камелкейсом с **большой** буквы.
  
  Мы вам настоятельно советуем всегда держать код в отформатированном виде во время разработки, со временем глаз привыкнет и вы почувствуете насколько это облегчает поиск ошибок в коде и его анализ. В любом случае, перед отправкой кода на проверку его обязательно нужно отформатировать, иначе он может быть отправлен на доработку без более глубокой проверки на этой итерации.


## Настроен Github CI с verify-сборкой Maven и JaCoCo в режиме генерации отчётов с покрытием на 100% по бранчам методов с логикой</summary>
  
  #### CI
  После связывания локального репозитория с удалённым и первого пуша в заготовки проекта, время настроить CI на основе Github Actions. Шаблон вашего maven.yml должен выглядеть вот так, убедитесь что всё совпадает с вашим шаблоном (например, что вы указали фазу `verify`, а не `package`):
  ```yml
  name: Java CI with Maven

  on: [push, pull_request]

  jobs:
    build:

      runs-on: ubuntu-latest

      steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
          distribution: 'adopt'
      - name: Build with Maven
        run: mvn -B -e verify
  ```
  
  #### JaCoCo

  ```xml
              <plugin>
                  <groupId>org.jacoco</groupId>
                  <artifactId>jacoco-maven-plugin</artifactId>
                  <version>0.8.5</version>
                  ...
  ```

  Инициализация:
  ```xml
                      <execution>
                          <id>prepare-agent</id>
                          <goals>
                              <goal>prepare-agent</goal>
                          </goals>
                      </execution>
  ```

  В режиме генерации отчётов:
  ```xml
                      <execution>
                          <id>report</id>
                          <phase>verify</phase>
                          <goals>
                              <goal>report</goal>
                          </goals>
                      </execution>
  ```

  В режиме проверки и обрушения сборки по уровню покрытия:
  ```xml
                      <execution>
                          <id>check</id>
                          <goals>
                              <goal>check</goal>
                          </goals>
                          <configuration>
                              <rules>
                                  <rule>
                                      <limits>
                                          <limit>
                                              <counter>LINE</counter>
                                              <value>COVEREDRATIO</value>
                                              <minimum>100%</minimum>
                                          </limit>
                                      </limits>
                                  </rule>
                              </rules>
                          </configuration>
                      </execution>
  ```
