+function ($) {
  'use strict';

  $.fn.Progressbar = function (options) {
    var self = this;
    var options = $.extend({}, $.fn.Progressbar.defaults, options);

    var subscribeDestination = "module/" + options['module'];

    var socket = new SockJS('/progressbar');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {

      stompClient.subscribe('/queue/' + subscribeDestination + '/progress', function(progress){
        var progressBar = JSON.parse(progress.body);
        if (progressBar.total == 0) {
          self.css('width', '100%');
        } else {
          self.css('width', (progressBar.current/progressBar.total)*100 + '%');
          self.text(progressBar.current + '/' + progressBar.total);
        }
      });

      stompClient.subscribe('/queue/' + subscribeDestination + '/finish', function(finish) {
        if (stompClient != null) {
          stompClient.disconnect();
        }
        $.each(options.enableBtns, function() {
          $(this.dom).prop("disabled", "");
          var btn = this;
          $(this.dom).on('click', function (event) {
            btn.clickCallback();
          });
        });
      });

      stompClient.subscribe("/queue/errors", function(message) {
        if (stompClient != null) {
          stompClient.disconnect();
        }
      });

      $.ajax({
        url: options.fetchData.url,
        type: options.fetchData.type,
        success: function() {

        },
        error : function() {
          if (stompClient != null) {
            stompClient.disconnect();
          }
        }
      });
    });
  }

  $.fn.Progressbar.defaults = {
    module: '',
    bizType: '',
    semester: '',
    enableBtns: [{
      dom: null,
      clickCallback: null
    }],
    fetchData: {
      url: '',
      data: {}
    }
  }
}(jQuery);