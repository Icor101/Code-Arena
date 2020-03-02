const fetch = require('node-fetch');
const parser = require('node-html-parser');

var url = 'https://clist.by'
var express = require("express")

var app = express();

function scrape(body,num){
	var obj = [];
	var nodeList = [];
	const dom = parser.parse(body);

	if(num==1){
		nodeList = dom.querySelectorAll('.row.contest.running');
	}
	else if(num==2){
		nodeList = dom.querySelectorAll('.row.contest.past');
	}
	else{
		nodeList = dom.querySelectorAll('.row.contest.coming');
	}

	for(var i=0;i<nodeList.length;i++){

		var json = {label:"",start_time:"",contest_title:"",platform:"",duration:""};

		if(num==1)
			json.label = "live";
		else if(num==2)
			json.label = "past";
		else
			json.label = "future";
		json.start_time = nodeList[i].querySelector('.col-md-5.col-sm-12.start-time').text.trim();
		json.duration = nodeList[i].querySelector('.col-md-3.col-sm-6.duration').text.trim();
		json.contest_title = nodeList[i].querySelector('.contest_title').querySelector('a').text.trim();
		json.platform = nodeList[i].querySelector('.text-muted').text.trim();

		if(json.contest_title==="")
			continue;
		obj.push(json);
	}
	return obj;
}

app.get('/live',(req,res) => {
	var p = Promise.resolve(fetch(url).then(res => res.text()).then(body => scrape(body,1)));
	p.then(function(v){
		res.send(v);
		console.log(v);
	},function(e){
		console.log(e);
	});
});

app.get('/past',(req,res) => {
	var p = Promise.resolve(fetch(url).then(res => res.text()).then(body => scrape(body,2)));
	p.then(function(v){
		res.send(v);
		console.log(v);
	},function(e){
		console.log(e);
	});
});

app.get('/upcoming',(req,res) => {
	var p = Promise.resolve(fetch(url).then(res => res.text()).then(body => scrape(body,3)));
	p.then(function(v){
		res.send(v);
		console.log(v);
	},function(e){
		console.log(e);
	});
});

const port = process.env.PORT || 3000;
var server = app.listen(port, () =>{
	console.log(`Listening to port ${port}...`);	
});