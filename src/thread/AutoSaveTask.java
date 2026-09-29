package thread;

import service.StudentManager;

public class AutoSaveTask implements Runnable {

    private final StudentManager manager;
    private volatile boolean running = true;

    public AutoSaveTask(StudentManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {

        while (running) {

            try {

                Thread.sleep(60000);

                if (running) {
                    manager.saveToFile();
                    System.out.println(
                            "\n[Auto-Save] Student data saved successfully."
                    );
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopTask() {
        running = false;
    }
}