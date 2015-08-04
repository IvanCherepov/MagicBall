package com.example.ivan.crystallball;

import java.util.Random;

/**
 * Created by ivan on 01/08/15.
 */
public class CrystallBall {
    //Member variables
    public String[] mAnswers = {
            "It is certain",
            "It is decidedly so",
            "All signs say YES",
            "The stars are not aligned",
            "My reply is no",
            "It is doubtful",
            "Better not tell you now",
            "Concentrate and ask again",
            "Unable to answer now"};

    //Methods
    public String getAnAnswer() {


        String answer = "";

        // Random answer
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mAnswers.length);

        answer = mAnswers[randomNumber];

        return answer;
    }
}
