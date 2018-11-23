# DSL Class Scheduling
Tugas IF4150 Rekayasa Perangkat Lunak Spesifik Domain

## Overview
This is a DSL (Domain Specific Languange) project made with [Antlr](https://www.antlr.org/) and Java. The domain is
about **Class Scheduling**. **Class Scheduling** is a domain that is very common 
in both universities and schools. Our aim is to create Class Scheduling DSL that is 
easy to use, do not have complex syntax, and provides most uses cases needed in 
real life class scheduling. 

In the DSL, we implement several use cases:
1. User can define Courses, Classrooms, Classes, and Lecturers. 
2. User can define Classrooms capacity and their facilities. 
3. User can specify Courses' facility needs. 
4. User can specify Lecturers' availability. 
5. User can specify Courses that should not be held at the same time. 
6. After user specify all the constraints above, user can automatically generate the schedule that satisfies all the constraints

## Running the project
1. Clone this repository.
2. Open IntelliJ IDEA, import as gradle project.
2. Install the [plugins](#plugins).
3. Execute ```gradle build```.
4. Run Main.java. 

## Writing the DSL
### 1. Examples
Examples can be viewed in the example [input file](http://gitlab.informatika.org/IF4150_13515034_13515057_13515063_13515124/dsl-class-scheduling/blob/master/src/main/resources/data.txt). 

### 2. Running the DSL
To run your DSL code, simply put your code in ```resources``` folder. In main program, change:

```InputStream is = Main.class.getResourceAsStream("data.txt");```

to

```InputStream is = Main.class.getResourceAsStream("yourFileName.yourFileExtension");```

## Documentation
https://docs.google.com/document/d/1PdZ7FJ2zcIwpBq8XltUSvhKnQsJdPg1CKU6YcXmI1-E

## Plugins
1. [Antlr](https://plugins.jetbrains.com/plugin/7358-antlr-v4-grammar-plugin)
2. [Lombok](https://plugins.jetbrains.com/plugin/6317-lombok-plugin)

## Contributors
| [<img src="https://avatars3.githubusercontent.com/u/23205832?s=400&v=4" width=60px style="border-radius: 50%;"><br /><sub>Roselina P<br />13515034</sub>](https://github.com/roselinapradjanata) | [<img src="https://avatars0.githubusercontent.com/u/20073050?s=400&u=881e4c44f50167fb8b447e608d8234d9adf369df&v=4" width=60px style="border-radius: 50%;"><br /><sub>Erick Wijaya<br />13515057</sub>](https://github.com/wijayaerick) | [<img src="https://avatars0.githubusercontent.com/u/26085823?s=400&v=4" width=60px style="border-radius: 50%;"><br /><sub>Kezia Suhendra<br />13515063</sub>](https://github.com/keziasuhendra) | [<img src="https://avatars3.githubusercontent.com/u/23205761?s=400&v=4" width=60px style="border-radius: 50%;"><br /><sub>Rachel Sidney<br />13515124</sub>](https://github.com/crahels) |
| :---: | :---: | :---: | :---: |