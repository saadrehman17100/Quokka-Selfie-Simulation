import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
class Quokka {
    private static int babiesBornToday = 0;
    private static int deathsToday = 0;
    private static int foodConsumedToday = 0;
    private String id;
    public boolean hasBaby;

    private int foodSupply;
    private boolean isAlive;
    private boolean selfieTaken;
    private int consecutiveDaysWithoutFood;
    private int totalDaysWithoutFood;

    public Quokka(String id, boolean hasBaby, int foodSupply, boolean isAlive) {
        this.id = id;
        this.hasBaby = hasBaby;
        this.foodSupply = hasBaby ? 3 : 2;
        this.isAlive = true;
        this.consecutiveDaysWithoutFood = 0;
        this.totalDaysWithoutFood = 0;

    }

    public int feed(int foodSup) {
        int requiredFood = hasBaby ? 3 : 2;

        if (foodSup >= requiredFood) {
            foodSup -= requiredFood;
            foodConsumedToday += requiredFood;
            consecutiveDaysWithoutFood = 0;
        } else {
            consecutiveDaysWithoutFood++;
            totalDaysWithoutFood++;

            if (consecutiveDaysWithoutFood >= 2) {
                isAlive = false;
                deathsToday++;
            }
        }
        return foodSup;
    }
    public void setBabiesBornToday() {
        if(hasBaby) {
            babiesBornToday++;
        }
    }
    public static int getBabiesBornToday() {
        return babiesBornToday;
    }
    public static int getDeathsToday() {
        return deathsToday;
    }
    public static int  getFoodConsumedToday() {
        return foodConsumedToday;
    }

    public static void resetDailyCounts() {
        babiesBornToday = 0;
        deathsToday = 0;
        foodConsumedToday = 0;
    }

    public void storeFood(int excessFood) {
        foodSupply += excessFood;
    }

    public int getConsecutiveDaysWithoutFood() {
        return consecutiveDaysWithoutFood;
    }

    public int getTotalDaysWithoutFood() {
        return totalDaysWithoutFood;
    }
    public boolean offerSelfie(SelfieType selfieType) {
        if (foodSupply > 0 && !selfieTaken) {
            foodSupply--;
            selfieTaken = true;
            return true;
        }
        return false;
    }

    public boolean isSelfieTaken() {
        return selfieTaken;
    }

    public void setSelfieTaken(boolean selfieTaken) {
        this.selfieTaken = selfieTaken;
    }

    public String getId() {
        return id;
    }
    public boolean hasBabyInPouch() {
        return hasBaby;
    }

    public int getFoodSupply() {
        return foodSupply;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
    public void setFoodSupply(int foodSupply) {
        this.foodSupply = foodSupply;
    }


    @Override
    public String toString() {
        return "Quokka{" +
                "id='" + id + '\'' +
                ", hasBaby=" + hasBaby +
                ", foodSupply=" + foodSupply +
                ", isAlive=" + isAlive +
                '}';
    }

    private static void FoodIntake(ArrayList<Quokka> quokkas) {
        int consecutiveDaysWithoutFood = 0;
        int totalDaysWithoutFood = 0;

        for (int day = 0; day < 30; day++) {
            for (Quokka quokka : quokkas) {
                int foodIntake = quokka.hasBabyInPouch() ? 3 : 2;
                quokka.setFoodSupply(quokka.getFoodSupply() + foodIntake);

                if (quokka.getFoodSupply() >= foodIntake) {
                    quokka.setFoodSupply(quokka.getFoodSupply() - foodIntake);
                    consecutiveDaysWithoutFood = 0;
                } else {
                    consecutiveDaysWithoutFood++;
                    totalDaysWithoutFood++;

                    if (consecutiveDaysWithoutFood >= 2) {
                        quokka.setAlive(false);
                    }
                }
            }
        }

        if (totalDaysWithoutFood >= 5) {
            for (Quokka quokka : quokkas) {
                if (quokka.isAlive()) {
                    quokka.setAlive(false);
                }
            }
        }
    }

}

class TouristGroup {
    int[] size;
    private boolean selfieTaken;
    public int groupSize;

    public int groupVideos;
    public int groupPhotos;
    public int photos;
    public int videos;
    public int sketch;
    public SelfieType selfieType;

    public TouristGroup(int[] size, boolean selfieTaken, int groupSize, int groupVideos, int groupPhotos, int photos, int videos, int sketch, SelfieType selfieType) {
        this.size = size;
        this.selfieTaken = selfieTaken;
        this.groupSize = groupSize;
        this.groupVideos = groupVideos;
        this.groupPhotos = groupPhotos;
        this.photos = photos;
        this.videos = videos;
        this.sketch = sketch;
        this.selfieType = selfieType;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public SelfieType getSelfieType() {
        return selfieType;
    }

    public int calculateFoodEarned(boolean hasBaby) {
        int foodEarned = 0;
        int[] selfies = {photos, videos, sketch, groupPhotos, groupVideos};
        if (selfieTaken) {
            foodEarned += selfies[0];
            foodEarned += selfies[3] * 2;
            for(int i = 0 ; i < selfies[1]; i++) {
                int random = ThreadLocalRandom.current().nextInt(60);
                if(random <= 20) {
                    foodEarned += 1;
                } else if(random <= 40) {
                    foodEarned += 2;
                }else{
                    foodEarned += 3;
                }
            }
            for(int i = 0 ; i < selfies[4]; i++) {
                int random = ThreadLocalRandom.current().nextInt(60);
                if(random <= 20) {
                    foodEarned += 2;
                } else if(random <= 40) {
                    foodEarned += 4;
                }else{
                    foodEarned += 6;
                }
            }
            for(int i = 0 ; i < selfies[2]; i++) {
                int rand = ThreadLocalRandom.current().nextInt(100);
                if(rand <= 50) {
                    foodEarned += 6;
                }
                else {
                    foodEarned += 10;
                }
            }
            if(hasBaby) {
                foodEarned *= 2;
            }

        }

        return foodEarned;
    }

    public void setSelfieTaken(boolean selfieTaken) {
        this.selfieTaken = selfieTaken;
    }
    Photo photo = new Photo("photo");
    Video video = new Video("video");
    Sketch sketch1 = new Sketch("sketch");
    @Override
    public String toString() {
        return "TouristGroup{" +
                "size=" + size +
                ", selfieType=" + selfieType +
                ", selfieTaken=" + selfieTaken +
                '}';
    }

    public boolean isSelfieTaken() {
        return false;
    }




}



enum SelfieType {
    INDIVIDUAL_PHOTO,INDIVIDUAL_VIDEO,INDIVIDUAL_SKETCH,GROUP_PHOTO,GROUP_VIDEO,GROUP_SKETCH,;
}

class Main {
    public static void main(String[] args) {
        int totalFoodInHand = 0;
        ArrayList<Integer> touristGroups = readTouristGroupsFromFile("tourist.txt");
        int populationSize = getPopulationSizeFromUserInput();
        ArrayList<Quokka> quokkas = createQuokkaCollection(populationSize);
        ArrayList<TouristGroup> touristData = createTouristData(touristGroups);
//        System.out.println("Tourist Groups: " + touristData);
//        System.out.println("Quokkas: " + quokkas);
        int babyCounter = 0;
        int dayCounter = 1;
        for (int i = 0; i < 30; i++) {
            int quokkaCounter = 0;

            for (Quokka quokka : quokkas) {
                quokkaCounter++;
                if (quokka.hasBabyInPouch()) {
                    babyCounter++;
                }
                totalFoodInHand += calculateInitialFoodSupply(quokka.hasBaby);

            }
            System.out.println("-----------Day "+ dayCounter + "-------------");
            dayCounter++;
            System.out.println("totalBaby = " + babyCounter);
            System.out.println("Current Alive Quokka = " + quokkaCounter);
            System.out.println("Current Total Food Bags Before Feeding = " + totalFoodInHand);
            for (Quokka quokka : quokkas) {
                totalFoodInHand = quokka.feed(totalFoodInHand);
            }
            System.out.println("Current Total Food Bags After Feeding = " + totalFoodInHand);


            System.out.println("Selfie Stats");
            System.out.println("Individual Photos = " + touristData.get(i).photos);
            System.out.println("Individual Videos = " + touristData.get(i).videos);
            System.out.println("Individual Sketch = " + touristData.get(i).sketch);
            System.out.println("Group Photos = " + touristData.get(i).groupPhotos);
            System.out.println("Group Videos = " + touristData.get(i).groupPhotos);
            int totalSelfies = touristData.get(i).photos + touristData.get(i).videos + touristData.get(i).sketch + touristData.get(i).groupPhotos + touristData.get(i).groupVideos;
            System.out.println("Total Selfies " + totalSelfies);
            totalFoodInHand += touristData.get(0).calculateFoodEarned(quokkas.get(0).hasBaby);
            System.out.println("Food Earned in Day " + totalFoodInHand);
            
            int[] ts=touristData.get(i).size;
            int touristCounter=0;
            for(int a:ts){
                touristCounter+=a;
            }

            System.out.println("Tourist Groups: " + touristCounter);
            System.out.println("Daily Summary ");
            System.out.println("Quokkas Dies Today " + Quokka.getDeathsToday());
            System.out.println("New Babies Today " + Quokka.getBabiesBornToday());
            System.out.println("Consumed Food Bags " +Quokka.getFoodConsumedToday());
            System.out.println("Current Food Bags " + totalFoodInHand);
            System.out.println();


            Scanner scanner = new Scanner(System.in);
            System.out.println("Press Enter key to continue...");
            try
            {
                System.in.read();
//                20
            }
            catch(Exception e)
            {}
        }

    }
    private static ArrayList<Integer> readTouristGroupsFromFile(String s) {
        ArrayList<Integer> touristGroups = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("tourist.txt"))) {
            String line = reader.readLine();
            String[] groupSizes = line.split(",");
            for (String size : groupSizes) {
                int groupSize = Integer.parseInt(size);
                touristGroups.add(groupSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return touristGroups;
    }

    private static int getPopulationSizeFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter The population Size");
        return scanner.nextInt();
    }

    private static ArrayList<Quokka> createQuokkaCollection(int populationSize) {
        ArrayList<Quokka> quokkas = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            String id = generateUniqueID(i);
            boolean hasBaby = hasBabyQuokkaInPouch();
            int initialFoodSupply = calculateInitialFoodSupply(hasBaby);
            boolean isAlive = true;

            Quokka quokka = new Quokka(id, hasBaby, initialFoodSupply, isAlive);
            quokkas.add(quokka);

        }
        return quokkas;
    }
    private static ArrayList<TouristGroup> createTouristData(ArrayList<Integer> touristGroups) {
        ArrayList<TouristGroup> touristData = new ArrayList<>();
        for(int groupSize : touristGroups) {
            int[] size = determineGroupSize(groupSize);
            int[] selfieCounts = determineSelfieType(size);
            boolean selfieTaken = true;

            TouristGroup touristGroup = new TouristGroup(size,selfieTaken,groupSize,selfieCounts[4],selfieCounts[3],selfieCounts[0],selfieCounts[1],selfieCounts[2],SelfieType.INDIVIDUAL_PHOTO);
            touristData.add(touristGroup);
        }
        return touristData;
    }
    private static int[] determineSelfieType(int[] size) {
        int idPhoto = 0,idVideo = 0,idSketch = 0;
        int grPhoto = 0,grVideo = 0;

        idPhoto += (int) Math.floor(size[0] * 0.6);
        idVideo += (int) Math.floor(size[0] * 0.35);
        idSketch += (int) Math.ceil(size[0] * 0.05);
        int totalRem = 0;
        for (int j : size) {
            totalRem += j;
        }
        grPhoto = (int) Math.floor((totalRem-size[0])*0.6);
        grVideo = (int) Math.floor((totalRem-size[0])*0.4);
        int sum = idVideo+idPhoto+idSketch+grPhoto+grVideo;

        if(sum < totalRem){
            grPhoto+= totalRem - sum;
        }else{
            grPhoto-= sum -totalRem;
        }
        return new int[]{idPhoto,idVideo,idSketch,grPhoto,grVideo};
    }
    private static int[] determineGroupSize(int groupSize){
        int groupOf1 = 0;
        int groupOf2 = 0;
        int groupOf3 = 0;
        int groupOf4 = 0;
        int groupOf5 = 0;
        int groupOf6 = 0;

        groupOf1=groupOf2 = (int) Math.floor(groupSize*0.3);
        groupOf3=groupOf4=groupOf5=groupOf6 = (int) Math.floor(groupSize*0.1);
        int arr[] = {groupOf1,groupOf2,groupOf3,groupOf4,groupOf5,groupOf6};
        int k = 1;
        for(int a:arr){
            k++;
        }
        return  arr;
    }


    private static String generateUniqueID(int index) {
        String idFormat = "Q%03d";
        return String.format(idFormat, index);
    }

    private static boolean hasBabyQuokkaInPouch() {
        return Math.random() < 0.1;
    }

    private static int calculateInitialFoodSupply(boolean hasBaby) {
        return hasBaby ? 3 : 2;
    }
}
