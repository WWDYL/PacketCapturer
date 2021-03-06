var express = require('express');
var router = express.Router();
var redis = require('redis'),
    client = redis.createClient();

client.on('error', function(err) {
    console.log('redis is error!');
});

router.get('/', function(req, res) {
    var jsondata = {"data" : []};
    client.get("attack:", function (err, reply) {
        // reply = 10;
        var timer = 1, timer_end = reply;

        for (var i = 1; i <= reply; ++i) {
            client.hgetall("attack:" + i, function(err, obj) {
                jsondata.data.push(obj);
                if (timer == timer_end) {
                    res.send(jsondata);
                }
                timer++;
            });
        }
    });

});

module.exports = router;