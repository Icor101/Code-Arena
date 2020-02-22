const fileSystem = require('fs');
const fetch = require('node-fetch');
const parser = require('node-html-parser');

var url = 'https://clist.by/'
var express = require("express")

var app = express();
var live = [], upcoming = [], past = [];

function scrape(body){
	
	const dom = parser.parse(body);
	var nodeList = dom.querySelectorAll('.row.contest.running');
	
	for(var i=0;i<nodeList.length;i++){
		
		var json = {start_time:"",contest_title:"",platform:"",duration:""};
		
		json.start_time = nodeList[i].querySelector('.col-md-5.col-sm-12.start-time').text.trim();
		json.duration = nodeList[i].querySelector('.col-md-3.col-sm-6.duration').text.trim();
		json.contest_title = nodeList[i].querySelector('.contest_title').querySelector('a').text.trim();
		json.platform = nodeList[i].querySelector('.text-muted').text.trim();
		
		live.push(json);
	}

	nodeList = dom.querySelectorAll('.row.contest.past');

	for(var i=0;i<nodeList.length;i++){
		
		var json = {start_time:"",contest_title:"",platform:"",duration:""};
		
		json.start_time = nodeList[i].querySelector('.col-md-5.col-sm-12.start-time').text.trim();
		json.duration = nodeList[i].querySelector('.col-md-3.col-sm-6.duration').text.trim();
		json.contest_title = nodeList[i].querySelector('.contest_title').querySelector('a').text.trim();
		json.platform = nodeList[i].querySelector('.text-muted').text.trim();
		
		past.push(json);
	}

	nodeList = dom.querySelectorAll('.row.contest.coming');

	for(var i=0;i<nodeList.length;i++){
		
		var json = {start_time:"",contest_title:"",platform:"",duration:""};
		
		json.start_time = nodeList[i].querySelector('.col-md-5.col-sm-12.start-time').text.trim();
		json.duration = nodeList[i].querySelector('.col-md-3.col-sm-6.duration').text.trim();
		json.contest_title = nodeList[i].querySelector('.contest_title').querySelector('a').text.trim();
		json.platform = nodeList[i].querySelector('.text-muted').text.trim();
		
		upcoming.push(json);
	}
}

fetch(url).then(res => res.text()).then(body => scrape(body));

app.get('/live',(req,res) => {
	res.send(live);
});

app.get('/past',(req,res) => {
	res.send(past);
});

app.get('/upcoming',(req,res) => {
	res.send(upcoming);
});


var server = app.listen(3000, () =>{
	console.log('Listening to port 3000...');	
});


