# spring-socket-progressbar

## example
demo 用的 bootstrap 的 progressbar

### java
```
  @Autowired
  protected ProgressbarFactory progressbarFactory;

  public void progress() throws Exception {

    ProgressbarBroker broker = new 	DefaultProgressbarBroker("DEMO");

    Progressbar bar = progressbarFactory.get(broker, 100);

    // 一些执行时间长的代码
    bar.init();

    for (int i = 0; i < 100; i++) {
      Thread.sleep(100);
      bar.increase();
    }

    bar.finish();

  }
```
### js
```
$(".progress-bar").Progressbar({
        module: 'DEMO',
        enableBtns: [{
          dom: $("#majorComplete"),
          clickCallback: function() {
            $("#majorProgram").modal('hide');
            $(".progress-bar").css('width', '0%');
            $(".progress-bar").text('0/0');
          }
        }],
        fetchData: {
          url : '/demo'
        }
      });

```
