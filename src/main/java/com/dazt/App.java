package com.dazt;

import com.dazt.model.Person;
import com.dazt.model.Product;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        Person p1 = new Person(1, "Diego", LocalDate.of(1986, 8, 22));
        Person p2 = new Person(2, "Alonso", LocalDate.of(1986, 8, 12));
        Person p3 = new Person(3, "Elena", LocalDate.of(1988, 3, 6));
        Person p4 = new Person(4, "Alina", LocalDate.of(2020, 5, 22));
        Person p5 = new Person(5, "Lex", LocalDate.of(2010, 12, 31));
        Person p6 = new Person(6, "Moona", LocalDate.of(2015, 5, 15));
        Person p7 = new Person(7, "Eva", LocalDate.of(2020, 5, 21));

        Product pr1 = new Product(1, "Ceviche", 15.0);
        Product pr2 = new Product(2, "Shashlik", 18.0);
        Product pr3 = new Product(3, "Ensalada", 12.0);
        Product pr4 = new Product(4, "Sopa", 8.0);
        Product pr5 = new Product(5, "Chilaquiles", 15.0);
        Product pr6 = new Product(6, "Quesadillas", 8.0);
        Product pr7 = new Product(7, "Ensalada", 11.50);

        List<Person> persons = Arrays.asList(p1,p2,p3,p4,p5,p6,p7);
        List<Product> products = Arrays.asList(pr1,pr2,pr3,pr4,pr5,pr6,pr7);
        System.out.println("1*********PROGRAMACION IMPERATIVA VS FUNCIONAL*********");
        System.out.println("****************PROGRAMACION IMPERATIVA****************");
        System.out.println("for (Person person : persons) {");
        System.out.println("    person");
        System.out.println("}");
        System.out.println("**********PROGRAMACION FUNCIONAL - java8-2014**********");
        System.out.println("************************ForEach************************");
        System.out.println("persons.forEach(System.out::println);");
        System.out.println("***********************RESULTADO***********************");
        persons.forEach(System.out::println);
        System.out.println("");

        System.out.println("************************FILTER*************************");
        List<Person> filteredPersons = persons.stream()
                .filter(p -> App.getAge(p.getBirthDate()) >= 18)
                .collect(Collectors.toList());
        App.printList(filteredPersons);
        System.out.println("");

        System.out.println("**************************MAP**************************");
        System.out.println("Recorre el arreglo original y devuelve otro arreglo    ");
        System.out.println("del valor que se ha solicitado");
        List<Integer> listMap = persons.stream()
                .map(p -> App.getAge(p.getBirthDate()))
                .collect(Collectors.toList());
        App.printList(listMap);

        System.out.println("***********************FUNCTION************************");
        Function<String, String> nameFunction = name -> "Tester " + name;
        List<String> listNames = persons.stream()
                .map(Person::getName)
                .map(nameFunction)
                .collect(Collectors.toList());
        App.printList(listNames);

        System.out.println("*****************SORTED - Comparator*******************");
        Comparator<Person> byNameAsc = (Person o1, Person o2) -> o1.getName().compareTo(o2.getName());
        List<Person> orderedList = persons.stream()
                .sorted(byNameAsc)
                .collect(Collectors.toList());
        App.printList(orderedList);

        System.out.println("******************Match - AnyMatch*********************");
        boolean blMatchD = persons.stream()
                .anyMatch(p-> p.getName().startsWith("D"));
        System.out.println("Existen nombre que empiecen con D: " + blMatchD);

        System.out.println("******************Match - AllMatch*********************");
        boolean blAllMatchD = persons.stream()
                .allMatch(p-> p.getName().startsWith("D"));
        System.out.println("Todos los nombres empiezan con D: " + blAllMatchD);

        System.out.println("*****************Match - NoneMatch*********************");
        boolean blNoneMatchD = persons.stream()
                .noneMatch(p-> p.getName().startsWith("D"));
        System.out.println("Todos los nombres no empiezan con D: " + blNoneMatchD);

        System.out.println("********************Skip - Limit***********************");
        int pageNumber = 1;
        int pageSize = 2;
        List<Person> pagedList = persons.stream()
                .skip(pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        App.printList(pagedList);

        System.out.println("****************Collectors - GroupBy*******************");
        Map<Double, List<Product>> listGroupedByPrice = products.stream()
                .filter(p->p.getPrice() > 5)
                .collect(Collectors.groupingBy(Product::getPrice));
        System.out.println(listGroupedByPrice);

        System.out.println("****************Collectors - Suming********************");
        Map<String, Long> mapGroupedProds = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getName, Collectors.counting()
                ));
        System.out.println(mapGroupedProds);

        System.out.println("****************Grouping & Suming********************");
        Map<String, Double> mapGroupSumingProds = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getName,
                        Collectors.summingDouble(Product::getPrice)
                ));
        System.out.println(mapGroupSumingProds);

        System.out.println("*********************Resuming**************************");
        DoubleSummaryStatistics statistics = products.stream()
                .collect(Collectors.summarizingDouble(Product::getPrice));
        System.out.println(statistics);

        System.out.println("**********************Reduce***************************");
        Optional<Double> sum = products.stream()
                .map(Product::getPrice)
                .reduce(Double::sum);
        System.out.println(sum.get());

    }

    public static int getAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public static void printList(List<?> list) {
        list.forEach(System.out::println);
    }
}
