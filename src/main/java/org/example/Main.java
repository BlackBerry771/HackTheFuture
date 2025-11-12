package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;
import java.nio.file.Paths;
import org.openqa.selenium.support.ui.Select;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Locate chromedriver one folder above the project directory
        String driverPath = Paths.get(System.getProperty("user.dir"), "..", "chromedriver.exe").toString();
        System.setProperty("webdriver.chrome.driver", driverPath);

        WebDriver driver = new ChromeDriver();

        try {
            // Open the target site
            driver.get("https://hackthefuture.bignited.be/home");

            // Wait for and click the button with class="center-button"
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(40));

            WebElement button = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(".center-button"))
            );

            button.click();



            //STARTUP PAGE
            //We clicked the man
            WebElement maleButton = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.id("male"))
            );

            // 4) Click the male button
            maleButton.click();

            List<WebElement> buttons = webDriverWait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.cssSelector(".buttons button")
                    )
            );

            Instant currentTime = Instant.now();

            WebElement confirmButton = buttons.stream()
                    .filter(b -> b.getText().trim().equals("Yes"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Button not found"));

            webDriverWait.until(d -> Duration.between(currentTime, Instant.now()).toSeconds() >= 3);

            confirmButton.click();

            Instant currentTime1 = Instant.now();

            WebElement nameInput = webDriverWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#whoami input[type='text']"))
            );
            nameInput.sendKeys("John Johnson");

            WebElement ageInput = driver.findElement(By.cssSelector("#whoami input[type='number']"));
            ageInput.sendKeys("30");  // replace with desired age

            // 5) Select country
            WebElement countrySelectElement = driver.findElement(By.cssSelector("#whoami select"));
            Select countrySelect = new Select(countrySelectElement);
            countrySelect.selectByVisibleText("Belgium");

            WebElement startButton = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("#whoami .btn.btn-primary.center-button")
                    )
            );

            webDriverWait.until(d -> Duration.between(currentTime1, Instant.now()).toSeconds() >= 4);



            startButton.click();




            //OFFICE PAGE
            Instant currentTime2 = Instant.now();
            webDriverWait.until(d -> Duration.between(currentTime2, Instant.now()).toSeconds() >= 2);

            //clicks the letters
            WebElement lettersDiv = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.id("letters"))
            );

            lettersDiv.click();

            Instant currentTime3 = Instant.now();

            //clicks to close the letter
            //Make sure the user has enough time to read the letter
            webDriverWait.until(d -> Duration.between(currentTime3, Instant.now()).toSeconds() >= 6);
            WebElement closeSpan = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.className("close"))
            );

            closeSpan.click();

            //clicks on the crystal on the ground
            WebElement crystalDiv = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.id("crystal"))
            );
            Instant currentTime4 = Instant.now();

            webDriverWait.until(d -> Duration.between(currentTime4, Instant.now()).toSeconds() >= 2);

            crystalDiv.click();

            //clicks on the large crystal in the center of the screen
            WebElement crystalImage = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.id("image-crystal"))
            );
            Instant currentTime5 = Instant.now();

            webDriverWait.until(d -> Duration.between(currentTime5, Instant.now()).toSeconds() >= 2);

            crystalImage.click();



            //SUBMARINE PAGE

            webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("#randomValuesScreen .randomValue"))
            );
            // 1. Get random value divs
            List<WebElement> randomValues = driver.findElements(By.cssSelector("#randomValuesScreen .randomValue"));

            // 2. Get switches
            List<WebElement> switches = driver.findElements(By.cssSelector("#switches .switch"));

            // 3. Iterate over values and click corresponding switch
            for (int i = 0; i < randomValues.size(); i++) {

                String valueText = randomValues.get(i).getText().trim();
                int clicks = valueText.equals("1") ? 1 : 2;

                WebElement currentSwitch = switches.get(i);

                Actions actions = new Actions(driver);
                for (int j = 0; j < clicks; j++) {
                    actions.moveToElement(currentSwitch).click().perform();
                    Thread.sleep(200); // small delay between clicks
                }
            }
            WebElement switchButton = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.id("button"))
            );
            Instant currentTime6 = Instant.now();
            webDriverWait.until(d -> Duration.between(currentTime6, Instant.now()).toSeconds() >= 2);

            switchButton.click();



            WebElement submarineButton = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.id("submarine"))
            );
            Instant currentTime7 = Instant.now();
            webDriverWait.until(d -> Duration.between(currentTime7, Instant.now()).toSeconds() >= 2);

            submarineButton.click();




            while (true) {
                try {
                    // Wait for a visible instruction image (timeout will break the loop if none appears)
                    WebElement instructionImage = webDriverWait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#instruction img"))
                    );

                    String imgSrc = instructionImage.getAttribute("src");
                    Keys keyToPress;

                    if (imgSrc.contains("left.png")) {
                        keyToPress = Keys.ARROW_LEFT;
                    } else if (imgSrc.contains("right.png")) {
                        keyToPress = Keys.ARROW_RIGHT;
                    } else if (imgSrc.contains("up.png")) {
                        keyToPress = Keys.ARROW_UP;
                    } else if (imgSrc.contains("down.png")) {
                        keyToPress = Keys.ARROW_DOWN;
                    } else {
                        throw new RuntimeException("Unknown instruction image: " + imgSrc);
                    }

                    // Send the key to the body
                    driver.findElement(By.tagName("body")).sendKeys(keyToPress);

                    // Optional small pause between actions
                    Instant currentTime8 = Instant.now();
                    webDriverWait.until(d -> Duration.between(currentTime8, Instant.now()).toMillis() >= 500);

                } catch (Exception e) {
                    // No more instructions appeared within the wait time, exit loop
                    break;
                }

            }
            WebDriverWait waitForLight = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement squareDiv = waitForLight.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".square"))
            );

            Actions actions = new Actions(driver);
            actions.doubleClick(squareDiv).perform();

            for (int i = 0; i < 4; i++) {
                String squareId = "square-" + i;

                // Wait for the square element to be present
                WebElement square = webDriverWait.until(
                        ExpectedConditions.presenceOfElementLocated(By.id(squareId))
                );

                // Hover over the square
                actions.moveToElement(square).perform();

                // Check if the "crystal glowing" element exists
                List<WebElement> glowingCrystals = driver.findElements(By.cssSelector(".crystal.glowing"));
                if (!glowingCrystals.isEmpty()) {
                    // Double-click the square if a glowing crystal exists
                    actions.doubleClick(square).perform();
                    break; // stop looping after double-click
                }
            }

            WebElement cubesContainer = webDriverWait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("draggable-cubes-container"))
            );

            // Get all cubes
            List<WebElement> cubes = cubesContainer.findElements(By.cssSelector("div")); // adjust selector if needed

            // Get all target elements
            List<WebElement> targets = driver.findElements(By.cssSelector("#word-target div[data-letter]"));

            // Keep track of used targets
            List<WebElement> usedTargets = new ArrayList<>();

            for (WebElement cube : cubes) {
                String letter = cube.getText().trim();

                // Find the first unused target with the same letter
                WebElement target = targets.stream()
                        .filter(t -> t.getAttribute("data-letter").equalsIgnoreCase(letter))
                        .filter(t -> !usedTargets.contains(t))
                        .findFirst()
                        .orElse(null);

                if (target != null) {
                    // Drag and drop the cube to the target
                    actions.dragAndDrop(cube, target).perform();
                    Thread.sleep(200); // optional small delay
                    usedTargets.add(target); // mark target as used
                }
            }

            // 1. Wait for the crystal and click it
            WebElement crystal = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.className("crystal"))
            );
            crystal.click();

            // 2. Click the crystal-outside element
            WebElement crystalOutside = webDriverWait.until(
                    ExpectedConditions.elementToBeClickable(By.className("crystal-outside"))
            );
            crystalOutside.click();

            // 3. Hold the crystal-inside for 6 seconds
            WebElement crystalInside = webDriverWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("crystal-inside"))
            );

            // Click and hold
            actions.clickAndHold(crystalInside).perform();

            // Wait for 6 seconds while holding
            Thread.sleep(5000);

            // Release the element
            actions.release().perform();


            WebElement gameContainer = webDriverWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("game-container"))
            );
            actions.sendKeys(Keys.SPACE).perform();


// Wait for boss and player to appear
            WebDriverWait waitForBossFight = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement boss = waitForBossFight.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".boss"))
            );
            WebElement player = waitForBossFight.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("character"))
            );

// Use the existing Actions and JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;

// Main movement + attack loop
            for (int i = 0; i < 200; i++) {
                try {
                    // Get the current x positions of both elements
                    Long bossX = ((Number) js.executeScript(
                            "return document.querySelector('.boss').getBoundingClientRect().x;"
                    )).longValue();

                    Long playerX = ((Number) js.executeScript(
                            "return document.getElementById('character').getBoundingClientRect().x;"
                    )).longValue();

                    if (bossX == null || playerX == null) break;

                    // Move player toward boss
                    if (playerX < bossX - 5) {
                        actions.sendKeys(Keys.ARROW_RIGHT).perform();
                    } else if (playerX > bossX + 5) {
                        actions.sendKeys(Keys.ARROW_LEFT).perform();
                    }

                    // Attack (space)
                    actions.sendKeys(Keys.SPACE).perform();

                    // Short delay between iterations
                    Thread.sleep(100);
                } catch (Exception e) {
                    break; // stop loop if boss disappears or an error occurs
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            // driver.quit();
        }
    }
}