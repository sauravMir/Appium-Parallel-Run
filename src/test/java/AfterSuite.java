public class AfterSuite {
    @org.testng.annotations.AfterTest(alwaysRun = true)
    public static void teardown() {
        BeforeSuites.tLocalDriver.get().quit();
        System.out.println("teardown");
    }
}
