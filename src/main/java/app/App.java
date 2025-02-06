package app;

import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.jackson.JacksonModule;
import io.jooby.netty.NettyServer;

import java.util.ArrayList;
import java.util.List;

public class App extends Jooby {

  private final List<Schedule> schedules = new ArrayList<>();
  private int idCounter = 1;

  public static class Schedule {
    public int id;
    public String title;
    public String date;

    public Schedule(int id, String title, String date) {
      this.id = id;
      this.title = title;
      this.date = date;
    }
  }

  {
    setServerOptions(new ServerOptions().setPort(8082));

    install(new NettyServer());
    install(new JacksonModule());

    schedules.add(new Schedule(idCounter++, "Meeting", "2025-02-07"));
    schedules.add(new Schedule(idCounter++, "Workout", "2025-02-08"));
    schedules.add(new Schedule(idCounter++, "Study", "2025-02-09"));

    get("/schedules", ctx -> schedules);

    post("/schedules", ctx -> {
      Schedule schedule = ctx.body(Schedule.class);
      schedule.id = idCounter++;
      schedules.add(schedule);
      return schedule;
    });
  }

  public static void main(final String[] args) {
    runApp(args, App::new);
  }

}
