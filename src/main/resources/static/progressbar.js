function Progressbar(identify) {
  var self = this;

  self.init = function() {
    var socket = new SockJS('/progressbar');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
      console.log('Connected: ' + frame);

      stompClient.subscribe('/queue/' + identify + '/progress', function(progress){
        console.log(JSON.parse(progress.body).content);
      });

      stompClient.subscribe('/queue/' + identify + '/finish', function(finish) {
        if (stompClient != null) {
          stompClient.disconnect();
        }
        console.log("Disconnected");
      });
    });
  }
}