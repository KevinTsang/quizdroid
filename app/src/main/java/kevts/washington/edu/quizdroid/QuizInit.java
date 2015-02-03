package kevts.washington.edu.quizdroid;


public class QuizInit {

    public static QuestionAnswer[] initializeMath() {
        QuestionAnswer mathQuestion1 = new QuestionAnswer("2+7=?",
                new String[] {"5", "9", "14", "20"});
        mathQuestion1.setCorrectAnswer(1);

        QuestionAnswer mathQuestion2 = new QuestionAnswer("20-3=?",
                new String[] {"17", "6", "12", "42"});
        mathQuestion2.setCorrectAnswer(0);

        QuestionAnswer mathQuestion3 = new QuestionAnswer("3*5=?",
                new String[] {"8", "16", "12", "15"});
        mathQuestion3.setCorrectAnswer(3);

        QuestionAnswer mathQuestion4 = new QuestionAnswer("42/7=?",
                new String[] {"5", "9", "6", "10"});
        mathQuestion4.setCorrectAnswer(2);

        return new QuestionAnswer[] {mathQuestion1, mathQuestion2, mathQuestion3, mathQuestion4};
    }

    public static QuestionAnswer[] initializePhysics() {
        QuestionAnswer physicsQuestion1 = new QuestionAnswer("What properties of an object are used to determine its momentum?",
                new String[] {"mass and velocity", "mass and acceleration", "force and weight", "your mom and gravity"});
        physicsQuestion1.setCorrectAnswer(0);

        QuestionAnswer physicsQuestion2 = new QuestionAnswer("Angular velocity describes an object moving:",
                new String[] {"in and out", "up and down", "in a circle", "like Schrodinger's cat"});
        physicsQuestion2.setCorrectAnswer(2);

        QuestionAnswer physicsQuestion3 = new QuestionAnswer("What is Newton's second law?",
                new String[] {"what comes around goes around", "F=ma", "V=IR", "craziness is proportional to hotness"});
        physicsQuestion3.setCorrectAnswer(1);

        QuestionAnswer physicsQuestion4 = new QuestionAnswer("Impulse measures which of the following?",
                new String[] {"what I do when I'm drunk", "work", "heartbeats", "change in momentum"});
        physicsQuestion4.setCorrectAnswer(3);

        return new QuestionAnswer[] {physicsQuestion1, physicsQuestion2, physicsQuestion3, physicsQuestion4};
    }

    public static QuestionAnswer[] initializeMSH() {
        QuestionAnswer mshQuestion1 = new QuestionAnswer("Which of the following is NOT part of X-Men?",
                new String[] {"Storm", "Cyclops", "Batman", "Professor X"});
        mshQuestion1.setCorrectAnswer(2);

        QuestionAnswer mshQuestion2 = new QuestionAnswer("In 'The Avengers' movie, who is the actor who plays Loki?",
                new String[] {"Chris Hemsworth", "Robert Downey Jr.", "Chris Evans", "Tom Hiddleston"});
        mshQuestion2.setCorrectAnswer(3);

        QuestionAnswer mshQuestion3 = new QuestionAnswer("What is the name of Thor's homeland?",
                new String[] {"Asgard", "Earth", "Gotham", "Metropolis"});
        mshQuestion3.setCorrectAnswer(0);

        QuestionAnswer mshQuestion4 = new QuestionAnswer("What mutant will appear in the 'Avengers: Age of Ultron' movie?",
                new String[] {"Wolverine", "Quicksilver", "Gambit", "Magneto"});
        mshQuestion4.setCorrectAnswer(1);

        return new QuestionAnswer[] {mshQuestion1, mshQuestion2, mshQuestion3, mshQuestion4};
    }
}
