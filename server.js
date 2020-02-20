const fileSystem = require('fs');
const fetch = require('node-fetch');
const parser = require('node-html-parser');

var url='https://clist.by/'

function scrape(body){
	
	const dom = parser.parse(body);
	var nodeList = dom.querySelectorAll('.row contest running');
	
	console.log(nodeList);
	
	for(var i=0;i<nodeList.length;i++){
		
		var json = {start_time:"",contest_title:"",platform:"",duration:""};
		
		json.start_time = nodeList[i].querySelector('.col-md-5 col-sm-12 start-time').text;
		json.duration = nodeList[i].querySelector('.col-md-3 col-sm-6 duration').text;
		json.contest_title = nodeList[i].querySelector('.contest_title').querySelector('a').text;
		json.platform = nodeList[i].querySelector('.text-muted').text;
		
		console.log("HELLO"+JSON.stringify(json));
	}
	
}

fetch(url).then(res => res.text()).then(body => scrape(body));

