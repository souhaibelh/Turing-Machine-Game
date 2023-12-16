package GeneralMethods;

import Model.*;
import Model.enums.Category;
import Model.verifiers.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * File methods
 */
public class FileMethods {

    /**
     * Method that reads from the file problems.csv where the problems are found with their descriptions, luck factor
     * , validators..., it creates a level for each then it returns the whole list.
     * @return List containing all the game levels
     */
    public static List<Level> getLevels() {
        String fileDirectory = "/problems.csv";
        InputStream in = TuringMachine.class.getResourceAsStream(fileDirectory);
        List<Level> levels = new ArrayList<>();

        try {
            assert in != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            int nbRead = 0;
            while ((line = reader.readLine()) != null) {
                if (nbRead == 0) {
                    nbRead++;
                    continue;
                }
                String[] lineContent = line.split(",");
                List<Integer> validateursNbs = new ArrayList<>();
                for (int i=4; i < lineContent.length; i++) {
                    validateursNbs.add(Integer.parseInt(lineContent[i]));
                }
                levels.add(
                        new Level(
                                Integer.parseInt(lineContent[0]),
                                Integer.parseInt(lineContent[1]),
                                Integer.parseInt(lineContent[2]),
                                new Code(lineContent[3]),
                                validateursNbs
                        )
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return levels;
    }

    /**
     * This method takes in a list of validatorNbs, and depending on the Nbr a certain validator will have to verify
     * for one thing or another, once these validators are all created we simply return the List containing all of them,
     * this list will be used later one to be added to a Level.
     * @param validatorNbs list describing the number of the validators
     * @return a list containing the TuringMachineVerifiers needed for each Level
     */
    public static List<TuringMachineVerifier> getValidators(List<Integer> validatorNbs) {
        List<TuringMachineVerifier> levelValidators = new ArrayList<>();
        for (Integer validatorIndex : validatorNbs) {
            switch (validatorIndex) {
                case 1:
                    levelValidators.add(new DigitValueVerifier(validatorIndex, 1, 0));
                    break;
                case 2:
                    levelValidators.add(new DigitValueVerifier(validatorIndex, 3, 0));
                    break;
                case 3:
                    levelValidators.add(new DigitValueVerifier(validatorIndex, 3, 1));
                    break;
                case 4:
                    levelValidators.add(new DigitValueVerifier(validatorIndex, 4, 1));
                    break;
                case 5:
                    levelValidators.add(new ParityVerifier(validatorIndex, 0));
                    break;
                case 6:
                    levelValidators.add(new ParityVerifier(validatorIndex, 1));
                    break;
                case 7:
                    levelValidators.add(new ParityVerifier(validatorIndex, 2));
                    break;
                case 8:
                    levelValidators.add(new OccurrenceVerifier(validatorIndex, 1));
                    break;
                case 9:
                    levelValidators.add(new OccurrenceVerifier(validatorIndex, 3));
                    break;
                case 10:
                    levelValidators.add(new OccurrenceVerifier(validatorIndex, 4));
                    break;
                case 11:
                    levelValidators.add(new DigitToDigitVerifier(validatorIndex, 0, 1));
                    break;
                case 12:
                    levelValidators.add(new DigitToDigitVerifier(validatorIndex, 0, 2));
                    break;
                case 13:
                    levelValidators.add(new DigitToDigitVerifier(validatorIndex, 1, 2));
                    break;
                case 14:
                    levelValidators.add(new ExtremumVerifier(validatorIndex, true));
                    break;
                case 15:
                    levelValidators.add(new ExtremumVerifier(validatorIndex, false));
                    break;
                case 16:
                    TuringMachineVerifier parityCounterVerifier = new TuringMachineVerifier(validatorIndex,
                            "Determines if there is more EVEN or ODD numbers in the code") {
                        @Override
                        public Category getResult(Code code) {
                            List<Integer> allDigits = code.getDigitContainer();
                            int evenCounter = 0;
                            int oddCounter = 0;
                            for (Integer x : allDigits) {
                                if (x % 2 == 0) {
                                    evenCounter++;
                                } else {
                                    oddCounter++;
                                }
                            }
                            return evenCounter > oddCounter ? Category.EVEN : Category.ODD;
                        }
                    };
                    levelValidators.add(parityCounterVerifier);
                    break;
                case 17:
                    TuringMachineVerifier counterEvenDigitsVerifier = new TuringMachineVerifier(validatorIndex, "Counts how" +
                            "many even numbers are in the code") {
                        @Override
                        public Category getResult(Code code) {
                            int evenCounter = 0;
                            for (Integer x : code.getDigitContainer()) {
                                if (x % 2 == 0) {
                                    evenCounter++;
                                }
                            }
                            return Category.getRepetitionCategory(evenCounter);
                        }
                    };
                    levelValidators.add(counterEvenDigitsVerifier);
                    break;
                case 18:
                    TuringMachineVerifier sumEvenOddVerifier = new TuringMachineVerifier(validatorIndex, "Determines if the " +
                            "sum of all digits in the code is Even or Odd") {
                        @Override
                        public Category getResult(Code code) {
                            int digitsSum = code.getDigitContainer().stream().mapToInt(digit -> digit).sum();
                            return Category.getParityCategory(digitsSum % 2);
                        }
                    };
                    levelValidators.add(sumEvenOddVerifier);
                    break;
                case 19:
                    TuringMachineVerifier compareSumValue = new TuringMachineVerifier(validatorIndex, "Compares the sum" +
                            " of the first digit and the second digit with the value 6") {
                        @Override
                        public Category getResult(Code code) {
                            Integer sum = code.getDigit(0) + code.getDigit(1);
                            return Category.getEqualityCategory(sum.compareTo(6));
                        }
                    };
                    levelValidators.add(compareSumValue);
                    break;
                case 20:
                    TuringMachineVerifier valueRepetition = new TuringMachineVerifier(validatorIndex, "Determines if a digit" +
                            " in the code repeats itself, and if it does, how many times") {
                        @Override
                        public Category getResult(Code code) {
                            List<Long> repetitions = countRepetitionsDistinct(code.getDigitContainer());
                            long max = repetitions.get(0);
                            for (Long x : repetitions) {
                                if (x >= max) {
                                    max = x;
                                }
                            }
                            return switch ((int) max) {
                                case 1 -> Category.NONE;
                                case 2 -> Category.ONE_REPETITIONS;
                                case 3 -> Category.TWO_REPETITIONS;
                                default -> null;
                            };
                        }
                    };
                    levelValidators.add(valueRepetition);
                    break;
                case 21:
                    TuringMachineVerifier twinDigitVerifier = new TuringMachineVerifier(validatorIndex, "Determines if a digit" +
                            "in the code appears exactly 2 times, no more, no less.") {
                        @Override
                        public Category getResult(Code code) {
                            return countRepetitionsDistinct(code.getDigitContainer()).contains(2) ? Category.TWO_REPETITIONS :
                                    Category.NONE;
                        }
                    };
                    levelValidators.add(twinDigitVerifier);
                    break;
                case 22:
                    TuringMachineVerifier orderAsDsValidator = new TuringMachineVerifier(validatorIndex, "Determines if the " +
                            "Code is in exclusively in ascending or descending order, if 2 or more values are equal, its none.") {
                        @Override
                        public Category getResult(Code code) {
                            if (IntStream.range(0, code.getDigitContainer().size() - 1)
                                    .allMatch(i -> code.getDigitContainer().get(i) < code.getDigitContainer().get(i + 1))) {
                                return Category.ASCENDING;
                            } else if (IntStream.range(0, code.getDigitContainer().size() - 1)
                                    .allMatch(i -> code.getDigitContainer().get(i) > code.getDigitContainer().get(i + 1))) {
                                return Category.DESCENDING;
                            } else {
                                return Category.NONE;
                            }
                        }
                    };
                    levelValidators.add(orderAsDsValidator);
                    break;
            }
        }
        return levelValidators;
    }

    /**
     * This method here counts the repetitions of Integers in a list and returns a list containing the repetitions of
     * each integer in the order it is situated in its original list
     * @param allDigits List containing the Integers
     * @return a list of Long containing the repetitions of each Integer as introduced in the method. Example:
     * input: 1,2,3,1
     * result: 2,1,1,2
     */
    private static List<Long> countRepetitionsDistinct(List<Integer> allDigits) {
        List<Long> repetitions = new ArrayList<>();
        for (Integer x : allDigits.stream().distinct().toList()) {
            repetitions.add(allDigits.stream().filter(digit -> digit.equals(x)).count());
        }
        return repetitions;
    }
}
