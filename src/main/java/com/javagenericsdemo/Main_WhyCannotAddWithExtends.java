package com.javagenericsdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates in detail why you cannot add elements to a List<? extends T> for OCP Java 21 preparation.
 * 
 * This is one of the most confusing aspects of generics for beginners and a common
 * area for exam questions. Understanding the underlying type-safety mechanisms
 * is crucial for both proper API design and passing the OCP Java 21 certification.
 * 
 * CORE CONCEPT:
 * The compiler enforces what's called "capture conversion" with wildcards. When you use
 * List<? extends Animal>, the compiler captures that as some specific but unknown type.
 * Since the exact type is unknown at compile time, the compiler cannot guarantee
 * type safety for any add operation, so it prohibits adding anything (except null).
 * 
 * MENTAL MODEL:
 * Imagine a "mystery box" labeled "Contains some kind of Animal". You can safely
 * take items out (you know they're at least Animals), but you can't put anything in
 * because you don't know what specific type of Animal the box is meant to hold.
 * 
 * REAL-WORLD ANALOGY:
 * Consider a veterinary clinic that has separate waiting rooms for different animal species.
 * If you're told "go to the waiting room for some type of mammal" (? extends Mammal),
 * you don't know which specific mammal room it is. It could be the dog room, cat room, etc.
 * You can safely observe the animals inside (they're all mammals), but you can't add
 * a new animal because you might put a dog in the cat waiting room.
 * 
 * OCP EXAM TIP:
 * The exam frequently tests this concept. Look for questions that:
 * - Ask what will/won't compile when using ? extends T
 * - Ask you to explain why certain operations are restricted
 * - Present code that tries to add elements to a producer collection
 */
public class Main_WhyCannotAddWithExtends {
    
    public static void main(String[] args) {
        System.out.println("=== Why You Cannot Add Elements to List<? extends T> ===");
        
        // Setup example class hierarchy
        // This visualization helps understand the subtyping relationships
        System.out.println("\nClass Hierarchy for our example:");
        System.out.println("          Animal");
        System.out.println("         /      \\");
        System.out.println("    Mammal      Bird");
        System.out.println("    /    \\");
        System.out.println("  Dog    Cat");
        
        // Create lists of different animal types
        // Note that each list holds a specific type in the hierarchy
        List<Dog> dogs = new ArrayList<>(Arrays.asList(new Dog("Fido"), new Dog("Rex")));
        List<Cat> cats = new ArrayList<>(Arrays.asList(new Cat("Whiskers"), new Cat("Fluffy")));
        List<Mammal> mammals = new ArrayList<>(Arrays.asList(new Dog("Spot"), new Cat("Mittens")));
        List<Animal> animals = new ArrayList<>(Arrays.asList(new Dog("Rover"), new Bird("Tweety")));
        
        // First, demonstrate what works - Reading from List<? extends Animal>
        // This is the "Producer Extends" pattern in action
        System.out.println("\n1. Reading from List<? extends Animal> (works fine):");
        
        printAnimals(dogs);   // List<Dog> is acceptable - Dogs are Animals
        printAnimals(cats);   // List<Cat> is acceptable - Cats are Animals
        printAnimals(mammals); // List<Mammal> is acceptable - Mammals are Animals
        printAnimals(animals); // List<Animal> is acceptable - exact match
        
        // Now, demonstrate why adding doesn't work
        System.out.println("\n2. Why Adding to List<? extends Animal> Doesn't Work:");
        
        // Assign a List<Dog> to a List<? extends Animal> reference
        // This is legal, but now the compiler only knows we have "some kind of Animal list"
        List<? extends Animal> someAnimals = dogs;
        
        // Let's try adding to it - all these operations would generate compile errors
        // someAnimals.add(new Dog("Bruno")); // Compile error!
        // someAnimals.add(new Cat("Smokey")); // Compile error!
        // someAnimals.add(new Animal("Generic")); // Compile error!
        
        System.out.println("None of the add operations compile! Here's why:");
        System.out.println(" - The variable someAnimals could be pointing to a List<Dog>, List<Cat>,");
        System.out.println("   List<Bird>, or any other List<? extends Animal>.");
        System.out.println(" - If it's a List<Dog>, then adding a Cat would break type safety.");
        System.out.println(" - If it's a List<Cat>, then adding a Dog would break type safety.");
        System.out.println(" - Even adding an Animal isn't safe because we don't know the specific type.");
        
        // The runtime visualization of the problem
        // This helps understand what's happening "behind the scenes"
        System.out.println("\n3. Visualizing the Problem:");
        System.out.println("Current someAnimals reference points to: " + someAnimals.getClass().getSimpleName());
        System.out.println("Contains: " + someAnimals);
        
        System.out.println("\nIf we could do this (which we can't):");
        System.out.println("someAnimals.add(new Cat(\"Tabby\"));");
        
        System.out.println("\nThen this would happen:");
        System.out.println("Dog dog = dogs.get(2); // ClassCastException: Cat cannot be cast to Dog");
        
        // Simulating with a raw type (don't do this in real code!)
        // This demonstrates the actual runtime issues that the compiler prevents
        System.out.println("\n4. Simulating the Problem with Raw Types (bad practice):");
        
        @SuppressWarnings("rawtypes")
        List rawDogs = dogs; // Raw type - bypasses generic type checking
        
        @SuppressWarnings("unchecked")
        boolean added = rawDogs.add(new Cat("Simba")); // This compiles but is UNSAFE!
        
        System.out.println("Added a Cat to dogs list using raw type: " + added);
        System.out.println("Current dogs list: " + dogs);
        System.out.println("Trying to process dogs normally now:");
        
        try {
            processDogs(dogs);
        } catch (ClassCastException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("This is exactly what the compiler prevents with proper generic usage!");
        }
        
        // Solution: Use the correct bounded wildcard
        System.out.println("\n5. Solution: Use the Right Tool for the Job");
        System.out.println("- To READ from a collection: List<? extends T> (Producer)");
        System.out.println("- To WRITE to a collection: List<? super T> (Consumer)");
        System.out.println("- To both READ and WRITE: List<T> (Exact type)");
        
        // Demonstration of proper approach
        List<Animal> animalList = new ArrayList<>();
        addMammals(animalList); // Using super bounded wildcard for adding
        System.out.println("\nAdded mammals to animal list: " + animalList);
    }
    
    /**
     * This method can READ from any list of Animals or their subtypes.
     * It demonstrates the "Producer Extends" part of PECS.
     * 
     * KEY INSIGHT:
     * When using List<? extends Animal>, we can safely extract elements because
     * we know they are at least Animals. The compiler guarantees this, allowing
     * us to use Animal methods on each element.
     * 
     * EXAM TIP:
     * On the OCP exam, you might see questions testing whether you understand
     * that you can safely iterate through or extract elements from a List<? extends T>,
     * even though you can't add to it.
     * 
     * @param animals A list of Animal or any subtype of Animal
     */
    public static void printAnimals(List<? extends Animal> animals) {
        System.out.println("Animals in list: ");
        for (Animal animal : animals) {
            // This is safe - we know all elements are at least Animals
            System.out.println(" - " + animal);
        }
    }
    
    /**
     * This method processes dogs specifically.
     * It will fail at runtime if the list contains non-Dog objects.
     * 
     * IMPORTANT SAFETY CONCEPT:
     * Type safety is both a compile-time and runtime concern. The generic
     * type system ensures safety at compile time, but if circumvented 
     * (as with raw types), runtime errors can occur.
     * 
     * @param dogs A list of Dog objects
     */
    public static void processDogs(List<Dog> dogs) {
        for (Dog dog : dogs) {
            // This assumes all elements are Dogs
            // If we bypassed type safety, this could fail at runtime
            dog.bark();
        }
    }
    
    /**
     * This method can ADD Mammals to any list that accepts Mammals or their supertypes.
     * It demonstrates the "Consumer Super" part of PECS.
     * 
     * KEY INSIGHT:
     * When using List<? super Mammal>, we can safely add Mammals or any subtype
     * of Mammal. This is because any list that can hold a Mammal can also hold
     * its subtypes (like Dog or Cat).
     * 
     * PRACTICAL APPLICATION:
     * This pattern is commonly used in collection utility methods that need to 
     * add elements to a collection, such as Collections.copy() destination parameter.
     * 
     * @param list A list of Mammal or any supertype of Mammal
     */
    public static void addMammals(List<? super Mammal> list) {
        // All of these adds are safe because we know the list accepts at least Mammals
        list.add(new Dog("Buddy"));
        list.add(new Cat("Felix"));
        // The commented line below would cause a compile error:
        // list.add(new Bird("Polly")); // Compile error! Bird is not a Mammal
    }
    
    // Class hierarchy for demonstration
    
    /**
     * Base Animal class that all other animals extend from.
     * 
     * This forms the root of our type hierarchy for demonstrating
     * generics with wildcards.
     */
    static class Animal {
        private String name;
        
        public Animal(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" + name + ")";
        }
    }
    
    /**
     * Mammal class - a direct subtype of Animal.
     * 
     * This represents an intermediate level in our hierarchy, with
     * multiple subtypes of its own.
     */
    static class Mammal extends Animal {
        public Mammal(String name) {
            super(name);
        }
    }
    
    /**
     * Bird class - another direct subtype of Animal.
     * 
     * This represents a parallel branch in our hierarchy to Mammal,
     * demonstrating type relationships that aren't in a direct line.
     */
    static class Bird extends Animal {
        public Bird(String name) {
            super(name);
        }
    }
    
    /**
     * Dog class - a specific type of Mammal.
     * 
     * This is a leaf class in our hierarchy, showing a concrete
     * implementation with specific behavior.
     */
    static class Dog extends Mammal {
        public Dog(String name) {
            super(name);
        }
        
        public void bark() {
            System.out.println(this + " says: Woof!");
        }
    }
    
    /**
     * Cat class - another specific type of Mammal.
     * 
     * This is another leaf class in our hierarchy, demonstrating
     * a sibling relationship with Dog under the Mammal parent.
     */
    static class Cat extends Mammal {
        public Cat(String name) {
            super(name);
        }
        
        public void meow() {
            System.out.println(this + " says: Meow!");
        }
    }
}
