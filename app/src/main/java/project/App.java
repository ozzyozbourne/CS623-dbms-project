package project;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        String text = "Hello";
        String justifiedText = String.format("%-"+12+"s1e212e", text ); // Right-justify within a width of 10 characters
        System.out.println(justifiedText);
        IntStream.range(0, 10).forEach(System.out::println);
    }
}
