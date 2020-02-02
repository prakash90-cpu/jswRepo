window.devicePixelRatio=window.devicePixelRatio||1;window.ChartUtils={drawCircle:function(d,i,h,a,j,f,b,g,c){d.beginPath();d.arc(i,h,a,Math.PI*j,Math.PI*f);d.closePath();if(g){d.fillStyle=b;d.fill()}else{d.width=c||1;d.strokeStyle=b;d.stroke()}},drawSector:function(d,i,h,a,j,f,b,g,c){d.beginPath();if((f-j)<2){d.moveTo(i,h)}d.arc(i,h,a,Math.PI*j,Math.PI*f);d.closePath();if(g){d.fillStyle=b;d.fill()}else{d.width=(c||1);d.strokeStyle=b;d.stroke()}},drawArc:function(b,a,i,g,f,h,d,c){b.beginPath();b.arc(a,i,g,Math.PI*f,Math.PI*h);b.strokeStyle=d;b.lineWidth=c|1;b.stroke()},drawLine:function(b,e,d,c){b.beginPath();b.moveTo(e[0].x,e[0].y);for(var a=1;a<e.length;a++){b.lineTo(e[a].x,e[a].y)}b.strokeStyle=d;b.lineWidth=c||1;b.stroke()},drawDashedLine:function(c,m,a,e,b){c.beginPath();c.moveTo(m[0].x,m[0].y);for(var l=1;l<m.length;l++){var o=m[l].x-m[l-1].x;var n=m[l].y-m[l-1].y;var k=Math.sqrt(o*o+n*n);e=e>k?k:e;var d=k/e;var h=o*e/k;var f=n*e/k;for(var g=0;g<d;g++){if(g%2){c.lineTo(m[l-1].x+h*g,m[l-1].y-f*g)}else{c.moveTo(m[l-1].x+h*g,m[l-1].y-f*g)}}}c.strokeStyle=a;c.lineWidth=b||1;c.stroke()},drawPolygon:function(b,f,e,d,c){b.beginPath();b.moveTo(f[0].x,f[0].y);for(var a=1;a<f.length;a++){b.lineTo(f[a].x,f[a].y)}b.closePath();if(d){b.fillStyle=e;b.fill()}else{b.strokeStyle=e;b.lineWidth=c||1;b.stroke()}},drawText:function(a,h,g,f,c,i,e,d,b){a.font=(i||12)+"px "+(e||"Microsoft YaHei");a.fillStyle=c;a.textAlign=d||"center";a.textBaseline=b||"middle";a.fillText(h,g||0,f||0)},drawMultiText:function(b,o,n,m,d,p,l,j,c,g){var k=this.getTextWidth(b,o,p,l);var e=Math.floor(o.length*g/k);var a=Math.ceil(o.length/e);for(var f=0;f<a;f++){var h=o.substr(e*f,e);window.ChartUtils.drawText(b,h,n,m+f*p*1.2,d,p,l,j,c)}},getMultiTextHeight:function(d,h,f,b,e){var g=this.getTextWidth(d,h,f,b);var a=Math.floor(h.length*e/g);var c=Math.ceil(h.length/a);return f*c*1.2},drawRoundRect:function(d,i,g,j,e,a,b,f,c){if(j<2*a){a=j/2}if(e<2*a){a=e/2}d.beginPath();d.moveTo(i+a,g);d.arcTo(i+j,g,i+j,g+e,a);d.arcTo(i+j,g+e,i,g+e,a);d.arcTo(i,g+e,i,g,a);d.arcTo(i,g,i+j,g,a);d.closePath();if(f){d.fillStyle=b;d.fill()}else{d.strokeStyle=b;d.width=c||1;d.stroke()}},getTextWidth:function(b,d,c,a){b.font=(c||12)+"px "+(a||"Microsoft YaHei");return b.measureText(d).width}};(function(a){a.fn.Exponent=function(u){var w={};var c=a(this);var d=null;var k=0;var f=0;var s=1;var p=[];var q=0;var o=0;var m=[];var h={x:0,y:0},j={x:0,y:0},b={x:0,y:0},r={x:0,y:0},t=0,l=0;var e={target:3,data:[],colors:[],background:"transparent",frames:60,isAnimation:true,animationTime:5,lineWidth:1,isDebug:false,isPercent:true,events:{start:function(x){},drawing:function(y,z,x){},end:function(x){}},axis:{x:["","1å¹´","2å¹´","3å¹´"],y:4,fontSize:10,fontFamily:"Microsoft YaHei",color:"#666666",lineColor:"#EEEEEE",lineWidth:1,manualY:false,minY:0,maxY:10,isShowMinY:true}};e=a.extend(true,e,u);function i(){var A,z;if(e.data.length>0){for(A=0;A<e.data.length;A++){p[A]=[];for(z=0;z<e.target;z+=0.1){p[A].push(Math.pow(e.data[A],z))}}}var y=p.length-e.colors.length;if(y>0){for(A=0;A<y;A++){e.colors.push(window.ColorUtils.randomColor());console.log(e.colors)}}var B=p.join(",").split(",");if(e.axis.manualY){t=Math.max(e.axis.minY,e.axis.maxY);l=Math.min(e.axis.minY,e.axis.maxY)}else{t=Math.max.apply(null,B);l=Math.min.apply(null,B)}var x=0;var D=0;var C;var E;if(e.axis.manualY){x=(t-l)/(e.axis.y+1);for(A=0;A<e.axis.y+1;A++){C=parseFloat((l+x*A).toFixed(3));C=e.isPercent?parseFloat((C*100).toFixed(3))+"%":C;m.push(C);E=window.ChartUtils.getTextWidth(d,C+"",e.axis.fontSize*s,e.axis.fontFamily);D=E>D?E:D}}else{x=(t-l)/(e.axis.y-1);for(A=0;A<e.axis.y+2;A++){C=parseFloat((l+x*(A-1)).toFixed(3));C=e.isPercent?parseFloat((C*100).toFixed(3))+"%":C;m.push(C);E=window.ChartUtils.getTextWidth(d,C+"",e.axis.fontSize*s,e.axis.fontFamily);D=E>D?E:D}}D+=5*s;E=window.ChartUtils.getTextWidth(d,e.axis.x[0]+"",e.axis.fontSize*s,e.axis.fontFamily)/2;D=E>D?E:D;h.x=b.x=D;h.y=j.y=0;j.x=r.x=f-window.ChartUtils.getTextWidth(d,e.axis.x[e.axis.x.length-1]+"",e.axis.fontSize*s,e.axis.fontFamily)/2;b.y=r.y=k-e.axis.fontSize*s-5*s*2;q=(j.x-h.x)/(e.axis.x.length-1);o=(b.y-h.y)/(e.axis.y+1);e.axis.minY=l-x;e.axis.maxY=t+x}function n(){var x;if(e.isDebug){window.ChartUtils.drawPolygon(d,[h,j,r,b,h],"green",false,s);window.ChartUtils.drawLine(d,[{x:b.x,y:b.y/2},{x:r.x,y:r.y/2}],"green",s)}window.ChartUtils.drawLine(d,[{x:h.x,y:h.y},{x:b.x,y:b.y},{x:r.x,y:r.y}],e.axis.lineColor,e.axis.lineWidth*s);for(x=1;x<e.axis.x.length-1;x++){window.ChartUtils.drawLine(d,[{x:h.x+q*x,y:h.y},{x:b.x+q*x,y:b.y}],e.axis.lineColor,e.axis.lineWidth*s)}for(x=0;x<e.axis.x.length;x++){window.ChartUtils.drawText(d,e.axis.x[x],b.x+q*x,b.y+5*s,e.axis.color,e.axis.fontSize*s,e.axis.fontFamily,"center","top")}for(x=(e.axis.isShowMinY?0:1);x<e.axis.y+1;x++){var y=[{x:h.x,y:b.y-o*x},{x:j.x,y:b.y-o*x}];window.ChartUtils.drawLine(d,y,e.axis.lineColor,e.lineWidth*s);window.ChartUtils.drawText(d,m[x],b.x-5*s,b.y-o*x,e.axis.color,e.axis.fontSize*s,e.axis.fontFamily,"right","middle")}}function g(H,x){var E=H/x;var F=(b.y-h.y)/(e.axis.maxY-e.axis.minY);for(var C=0;C<p.length;C++){var B=p[C];var G=[];var D=(j.x-h.x)/(B.length-1);for(var z=0;z<=(B.length-1)*E;z++){G.push({x:b.x+D*z,y:b.y-(B[z]-e.axis.minY)*F})}var A=(B.length-1)*E%1;if(A>0&&A<1){var y=0;y=(B[z]-B[z-1])*A;G.push({x:b.x+(r.x-b.x)*E,y:b.y-(B[z-1]+y-e.axis.minY)*F})}window.ChartUtils.drawLine(d,G,e.colors[C],e.lineWidth*s)}}function v(){if(e.events&&e.events.start){e.events.start(e)}var x=e.isAnimation?0:1;var y=1;i();var z=setInterval(function(){if(e.events&&e.events.drawing){e.events.drawing(x,y,e)}d.clearRect(0,0,f,k);d.fillStyle=e.background;d.fillRect(0,0,f,k);x=x+1/(e.animationTime*1000/e.frames)>y?y:x+1/(e.animationTime*1000/e.frames);n();g(x,y);if(x==y){clearInterval(z);if(e.events&&e.events.end){e.events.end(e)}}},1000/e.frames)}w.draw=function(){if(e.isDebug){console.log(e)}s=window.devicePixelRatio;d=c[0].getContext("2d");k=c.height()*s;f=c.width()*s;f=f==0?k:f;k=k==0?f:k;c.attr("height",k);c.attr("width",f);v()};return w}})(jQuery);(function(a){a.fn.Line=function(u){var w={};var c=a(this);var d=null;var k=0;var f=0;var s=1;var p=[];var q=0;var o=0;var m=[];var h={x:0,y:0},j={x:0,y:0},b={x:0,y:0},r={x:0,y:0},t=0,l=0;var e={data:[],colors:["#99d1fd","#fed27c"],isArea:[true,false],background:"transparent",frames:60,isAnimation:true,animationTime:5,lineWidth:1,isDebug:false,events:{start:function(x){},drawing:function(y,z,x){},end:function(x){}},axis:{x:[],y:4,fontSize:10,fontFamily:"Microsoft YaHei",color:"#666666",lineColor:"#EEEEEE",lineWidth:1,isPercent:false,precision:2,manualY:true,minY:0,maxY:100}};e=a.extend(true,e,u);function i(){var z;if(e.data.length>0){if(typeof e.data[0]=="number"){p[0]=e.data}else{p=e.data}}var y=p.length-e.colors.length;if(y>0){for(z=0;z<y;z++){e.colors.push(window.ColorUtils.randomColor());console.log(e.colors)}}var A=e.data.join(",").split(",");if(e.axis.manualY){t=Math.max(e.axis.minY,e.axis.maxY);l=Math.min(e.axis.minY,e.axis.maxY)}else{t=Math.max.apply(null,A);l=Math.min.apply(null,A)}var x=0;var C=0;var B;var D;if(e.axis.manualY){x=(t-l)/(e.axis.y+1);for(z=0;z<e.axis.y+1;z++){if(e.axis.isPercent){B=parseFloat((l+x*z).toFixed(e.axis.precision))}else{B=parseFloat(((l+x*z)*100).toFixed(e.axis.precision))+"%"}m.push(B);D=window.ChartUtils.getTextWidth(d,B+"",e.axis.fontSize*s,e.axis.fontFamily);C=D>C?D:C}}else{x=(t-l)/(e.axis.y-1);for(z=0;z<e.axis.y+2;z++){if(e.axis.isPercent){B=parseFloat(((l+x*(z-1))*100).toFixed(e.axis.precision))+"%"}else{B=parseFloat((l+x*(z-1)).toFixed(e.axis.precision))}m.push(B);D=window.ChartUtils.getTextWidth(d,B+"",e.axis.fontSize*s,e.axis.fontFamily);C=D>C?D:C}}C+=5*s;D=window.ChartUtils.getTextWidth(d,e.axis.x[0]+"",e.axis.fontSize*s,e.axis.fontFamily)/2;C=D>C?D:C;h.x=b.x=C;h.y=j.y=0;j.x=r.x=f-window.ChartUtils.getTextWidth(d,e.axis.x[e.axis.x.length-1]+"",e.axis.fontSize*s,e.axis.fontFamily)/2;b.y=r.y=k-e.axis.fontSize*s-5*s*2;q=(j.x-h.x)/(e.axis.x.length-1);o=(b.y-h.y)/(e.axis.y+1);e.axis.minY=l-x;e.axis.maxY=t+x}function n(){var x;if(e.isDebug){window.ChartUtils.drawPolygon(d,[h,j,r,b,h],"green",false,s);window.ChartUtils.drawLine(d,[{x:b.x,y:b.y/2},{x:r.x,y:r.y/2}],"green",s)}window.ChartUtils.drawLine(d,[{x:h.x,y:h.y},{x:b.x,y:b.y},{x:r.x,y:r.y}],e.axis.lineColor,e.axis.lineWidth*s);for(x=1;x<e.axis.x.length-1;x++){window.ChartUtils.drawLine(d,[{x:h.x+q*x,y:h.y},{x:b.x+q*x,y:b.y}],e.axis.lineColor,e.axis.lineWidth*s)}for(x=0;x<e.axis.x.length;x++){window.ChartUtils.drawText(d,e.axis.x[x],b.x+q*x,b.y+5*s,e.axis.color,e.axis.fontSize*s,e.axis.fontFamily,"center","top")}for(x=1;x<e.axis.y+1;x++){var y=[{x:h.x,y:b.y-o*x},{x:j.x,y:b.y-o*x}];window.ChartUtils.drawLine(d,y,e.axis.lineColor,e.lineWidth*s);window.ChartUtils.drawText(d,m[x],b.x-5*s,b.y-o*x,e.axis.color,e.axis.fontSize*s,e.axis.fontFamily,"right","middle")}}function g(H,x){var E=H/x;var F=(b.y-h.y)/(e.axis.maxY-e.axis.minY);for(var C=0;C<p.length;C++){var B=p[C];var G=[];var D=(j.x-h.x)/(B.length-1);for(var z=0;z<=(B.length-1)*E;z++){G.push({x:b.x+D*z,y:b.y-(B[z]-e.axis.minY)*F})}var A=(B.length-1)*E%1;if(A>0&&A<1){var y=0;y=(B[z]-B[z-1])*A;G.push({x:b.x+(r.x-b.x)*E,y:b.y-(B[z-1]+y-e.axis.minY)*F})}window.ChartUtils.drawLine(d,G,e.colors[C],e.lineWidth*s);if(e.isArea[C]){G.unshift({x:b.x,y:b.y});G.push({x:G[G.length-1].x,y:r.y});window.ChartUtils.drawPolygon(d,G,window.ColorUtils.opacityColor(e.colors[C],0.3),e.lineWidth*s)}}}function v(){if(e.events&&e.events.start){e.events.start(e)}var x=e.isAnimation?0:1;var y=1;i();var z=setInterval(function(){if(e.events&&e.events.drawing){e.events.drawing(x,y,e)}d.clearRect(0,0,f,k);d.fillStyle=e.background;d.fillRect(0,0,f,k);x=x+1/(e.animationTime*1000/e.frames)>y?y:x+1/(e.animationTime*1000/e.frames);n();g(x,y);if(x==y){clearInterval(z);if(e.events&&e.events.end){e.events.end(e)}}},1000/e.frames)}w.draw=function(){if(e.isDebug){console.log(e)}s=window.devicePixelRatio;d=c[0].getContext("2d");k=c.height()*s;f=c.width()*s;f=f==0?k:f;k=k==0?f:k;c.attr("height",k);c.attr("width",f);v()};return w}})(jQuery);(function(a){a.fn.Meter=function(p){var t={};var d=a(this);var e=null;var i=0;var g=0;var k={x:0,y:0};var n=1;var q=1;var r=0;var f={target:-1,min:0,max:100,background:"transparent",frames:60,startAngle:0.8,endAngle:2.2,isAnimation:true,animationTime:3,isDebug:false,events:{start:function(u){},drawing:function(v,w,u){},end:function(u){}},colors:["#ff6131","#ffad1f","#4ebf42","#317fff"],title:{text:"",fontSize:18,fontFamily:"Microsoft YaHei",color:"#333333"},subTitle:{text:"",fontSize:14,fontFamily:"Microsoft YaHei",color:"#333333"},arc:{type:0,defaultColor:"rgba(51, 51, 51,0.2)",targetColor:"#FFFFFF",width:1,pointRadius:6},tick:{type:0,length:10,width:1,defaultColor0:"#3c3c3c",defaultColor1:"#3c3c3c",targetColor:"#3c3c3c"},tickText:{fontSize:10,color:"#3c3c3c",fontFamily:"Microsoft YaHei"},scoreText:{fontSize:50,fontFamily:"Microsoft YaHei",type:0,color:"#333333",precision:2}};f=a.extend(true,f,p);function m(u,v){return k.x+u*Math.cos(v*Math.PI)}function c(u,v){return u*Math.cos(v*Math.PI)}function l(u,v){return k.y+u*Math.sin(v*Math.PI)}function b(u,v){return u*Math.sin(v*Math.PI)}function o(z){var x;var w;var v;var u;var A;var y;if(z!=undefined){y=z/f.max}for(x=0;x<51;x++){w=f.radius+(x%5==0?f.tickText.spacing+f.tick.length*n:f.tickText.spacing+f.tick.length*n-2*n);v=f.radius+(x%5==0?f.tickText.spacing:f.tickText.spacing+2*n);u=(f.radius+v)/2;A=f.startAngle+(f.endAngle-f.startAngle)/50*x;window.ChartUtils.drawLine(e,[{x:m(w,A),y:l(w,A)},{x:m(v,A),y:l(v,A)}],x/51<y?(f.tick.type==0?window.ColorUtils.getColorStop(0,51,x,f.colors):f.tick.targetColor):(x%5==0?f.tick.defaultColor1:f.tick.defaultColor0),f.tick.width*n);if(x%10==0){window.ChartUtils.drawText(e,f.max/50*x,m(u,A),l(u,A),f.tickText.color,f.tickText.fontSize*n,f.tickText.fontFamily,"center","middle")}}}function j(){if(f.isDebug){window.ChartUtils.drawLine(e,[{x:k.x,y:0},{x:k.x,y:i}],"red",1);window.ChartUtils.drawLine(e,[{x:0,y:k.y},{x:g,y:k.y}],"red",1)}window.ChartUtils.drawArc(e,k.x,k.y,f.radius,f.startAngle,f.endAngle,f.arc.defaultColor,f.arc.width*n);o()}function h(u){var K;var v=u/f.max;o(u);if(f.arc.type==0){var N=m(f.radius,f.startAngle),A=l(f.radius,f.startAngle),M=k.x,z=l(f.radius,1.5),L=m(f.radius,f.endAngle),w=l(f.radius,f.endAngle);var G=e.createLinearGradient(N,A,M,z);var I=e.createLinearGradient(L,w,M,z);var F=window.ColorUtils.getColorStop(f.min,f.max,(f.max-f.min)/2,f.colors);f.colorStop1=[];f.colorStop2=[];var O=parseInt(f.colors.length/2);var P=(f.endAngle-f.startAngle)/(f.colors.length-1);for(K=0;K<f.colors.length;K++){if(f.colors.length%2!=0){if(K<=O){f.colorStop1.push({color:f.colors[K],angle:f.startAngle+P*K})}if(K>=O){f.colorStop2.push({color:f.colors[K],angle:f.startAngle+P*K})}}else{if(K<O){f.colorStop1.push({color:f.colors[K],angle:f.startAngle+P*K})}if(K==O){f.colorStop1.push({color:F,angle:(f.endAngle+f.startAngle)/2});f.colorStop2.push({color:F,angle:(f.endAngle+f.startAngle)/2})}if(K>=O){f.colorStop2.push({color:f.colors[K],angle:f.startAngle+P*K})}}}var B;var J=f.radius*(1+Math.sin(f.startAngle*Math.PI));for(K=0;K<f.colorStop1.length;K++){B=(b(f.radius,f.startAngle)-b(f.radius,f.colorStop1[K].angle))/J;G.addColorStop(B,f.colorStop1[K].color)}for(K=0;K<f.colorStop2.length;K++){B=(b(f.radius,f.endAngle)-b(f.radius,f.colorStop2[K].angle))/J;I.addColorStop(B,f.colorStop2[K].color)}if(f.isDebug){window.ChartUtils.drawLine(e,[{x:N,y:A},{x:M,y:z}],G,f.tick.width*n);window.ChartUtils.drawLine(e,[{x:L,y:w},{x:M,y:z}],I,f.tick.width*n)}if(v<=0.5){window.ChartUtils.drawArc(e,k.x,k.y,f.radius,f.startAngle,f.startAngle+(f.endAngle-f.startAngle)*v,G,f.arc.width*n)}else{window.ChartUtils.drawArc(e,k.x,k.y,f.radius,f.startAngle,f.startAngle+(f.endAngle-f.startAngle)*0.5,G,f.arc.width*n);window.ChartUtils.drawArc(e,k.x,k.y,f.radius,f.startAngle+(f.endAngle-f.startAngle)*0.5,f.startAngle+(f.endAngle-f.startAngle)*v,I,f.arc.width*n)}}else{window.ChartUtils.drawArc(e,k.x,k.y,f.radius,f.startAngle,f.startAngle+(f.endAngle-f.startAngle)*v,f.arc.targetColor,f.arc.width*n)}var E=m(f.radius,(f.startAngle+(f.endAngle-f.startAngle)*v));var D=l(f.radius,(f.startAngle+(f.endAngle-f.startAngle)*v));var H=e.createRadialGradient(E,D,0,E,D,6*n);var C=f.arc.type==0?window.ColorUtils.getColorStop(f.min,f.max,u,f.colors):f.arc.targetColor;H.addColorStop(0,C);H.addColorStop(0.5,C);H.addColorStop(1,window.ColorUtils.opacityColor(C,0.3));window.ChartUtils.drawCircle(e,E,D,f.arc.pointRadius*n,0,2,H,true);window.ChartUtils.drawText(e,parseFloat(u.toFixed(f.scoreText.precision)),k.x,f.title.text?k.y-f.scoreText.fontSize/2*n:k.y,f.scoreText.type==0?window.ColorUtils.getColorStop(f.min,f.max,u,f.colors):f.scoreText.color,f.scoreText.fontSize*n,f.scoreText.fontFamily,"center","middle");if(f.title.text){window.ChartUtils.drawText(e,f.title.text,k.x,k.y+f.title.fontSize/2*n+f.radius*0.1,f.title.color,f.title.fontSize*n,f.scoreText.fontFamily,"center","middle")}}function s(){if(f.events&&f.events.start){f.events.start()}if(f.target>f.max){f.target=f.max}if(f.target<f.min){if(f.events&&f.events.drawing){f.events.drawing()}e.clearRect(0,0,g,i);e.fillStyle=f.background;e.fillRect(0,0,g,i);j();window.ChartUtils.drawText(e,"æš‚æ— ",k.x,f.title.text?k.y-f.scoreText.fontSize/2*n:k.y,f.scoreText.color,f.scoreText.fontSize*n,f.scoreText.fontFamily,"center","middle");if(f.title.text){window.ChartUtils.drawText(e,f.title.text,k.x,k.y+f.title.fontSize/2*n+f.radius*0.1,f.title.color,f.title.fontSize*n,f.scoreText.fontFamily,"center","middle")}if(f.subTitle.text){var v=((i-r)+Math.max(l(f.radius+f.tickText.spacing+f.tick.length*n,f.startAngle),l(f.radius+f.tickText.spacing+f.tick.length*n,f.endAngle)))/2;window.ChartUtils.drawMultiText(e,f.subTitle.text,k.x,v,f.subTitle.color,f.subTitle.fontSize*n,f.subTitle.fontFamily,"center","top",g*0.9)}if(f.events&&f.events.end){f.events.end()}return}var w=f.target;var u=f.isAnimation?0:f.target;var x=setInterval(function(){if(f.events&&f.events.drawing){f.events.drawing(u)}e.clearRect(0,0,g,i);e.fillStyle=f.background;e.fillRect(0,0,g,i);u=u+(f.max-f.min)/(f.animationTime*1000/60)>w?w:u+(f.max-f.min)/(f.animationTime*1000/60);j();h(u);if(u==w){clearInterval(x);if(f.subTitle.text){var y=((i-r)+Math.max(l(f.radius+f.tickText.spacing+f.tick.length*n,f.startAngle),l(f.radius+f.tickText.spacing+f.tick.length*n,f.endAngle)))/2;window.ChartUtils.drawMultiText(e,f.subTitle.text,k.x,y,f.subTitle.color,f.subTitle.fontSize*n,f.subTitle.fontFamily,"center","top",g*0.9)}if(f.events&&f.events.end){f.events.end()}}},1000/f.frames)}t.draw=function(){n=window.devicePixelRatio;e=d[0].getContext("2d");i=d.height()*n;g=d.width()*n;g=g==0?i:g;i=i==0?g:i;q=Math.min(g,i)/250;d.attr("height",i);d.attr("width",g);var v=window.ChartUtils.getTextWidth(e,f.max,f.tickText.fontSize*n,f.tickText.fontFamily);var u=f.tickText.spacing=Math.sqrt(f.tickText.fontSize*n*f.tickText.fontSize*n+v*v);if(f.subTitle.text){r=window.ChartUtils.getMultiTextHeight(e,f.subTitle.text,f.subTitle.fontSize*n,f.subTitle.fontFamily,g*0.9)}k.x=g/2;k.y=(i-r)/(1+Math.sin(f.startAngle));f.radius=Math.min(k.x,k.y)-f.arc.width*n-u-f.tick.length*n;if(f.isDebug){console.log(f);console.log("åœ†å¿ƒä½ç½®ï¼š X: "+k.x+"  y: "+k.y);console.log("åŠå¾„ï¼š"+f.radius);console.log("åˆ»åº¦æ–‡å­—çš„å®½åº¦ï¼š"+u);console.log("å‰¯æ ‡é¢˜çš„é«˜åº¦ï¼š"+r)}s()};return t}})(jQuery);(function(a){a.fn.Pie=function(d){var e={};var f=a(this);var c=null;var j=0;var k=0;var g={x:0,y:0};var h=1;var l={data:[],colors:[],spacing:30,background:"transparent",frames:60,startAngle:1,isAnimation:true,animationTime:3,defaultColor:"#eff5f6",isDebug:false,events:{start:function(m){},drawing:function(n,o,m){},end:function(m){}},proportion:{isShow:true,fontSize:10,fontFamily:"Microsoft YaHei",textColor:"#333333",lineColor:"#333333",lineWidth:1,lineLength:20},title:{text:"",fontSize:20,fontFamily:"Microsoft YaHei",color:"#333333"},subTitle:{text:"",fontSize:14,fontFamily:"Microsoft YaHei",color:"#333333"}};l=a.extend(true,l,d);function b(x,p){var s=x/p;var t=[];var r=0;var q;for(q=0;q<l.data.length;q++){r+=l.data[q]}for(q=0;q<l.data.length;q++){t[q]={start:q==0?l.startAngle:t[q-1].end,end:(q==0?l.startAngle:t[q-1].end)+2*l.data[q]/r*s};window.ChartUtils.drawArc(c,g.x,g.y,l.radius,t[q].start,t[q].end,l.colors[q],l.spacing);if(l.proportion.isShow){var o=g.x+l.radius*Math.cos((t[q].start+t[q].end)/2*Math.PI);var v=g.y+l.radius*Math.sin((t[q].start+t[q].end)/2*Math.PI);var n=g.x+(l.radius+l.spacing/2+l.proportion.lineLength)*Math.cos((t[q].start+t[q].end)/2*Math.PI);var u=g.y+(l.radius+l.spacing/2+l.proportion.lineLength)*Math.sin((t[q].start+t[q].end)/2*Math.PI);c.font=l.proportion.fontSize*h+"px "+l.proportion.fontFamily;var w=" "+parseFloat((l.data[q]/r*100*s).toFixed(2))+"% ";var m=n+(n>g.x?c.measureText(w).width:-c.measureText(w).width);window.ChartUtils.drawLine(c,[{x:o,y:v},{x:n,y:u},{x:m,y:u}],l.proportion.lineColor,l.proportion.lineWidth*h);window.ChartUtils.drawText(c,w,n>g.x?n:m,u,l.proportion.textColor,l.proportion.fontSize*h,l.proportion.fontFamily,"left","bottom")}}if(l.subTitle.text){window.ChartUtils.drawText(c,l.title.text,g.x,g.y,l.title.color,l.title.fontSize*h,l.title.fontFamily,"center","bottom");window.ChartUtils.drawText(c,l.subTitle.text,g.x,g.y,l.subTitle.color,l.subTitle.fontSize*h,l.subTitle.fontFamily,"center","top")}else{window.ChartUtils.drawText(c,l.title.text,g.x,g.y,l.title.color,l.title.fontSize*h,l.title.fontFamily,"center","middle")}}function i(){if(l.events&&l.events.start){l.events.start(l)}var n=l.isAnimation?0:1;var o=1;if(l.data.length==0){l.colors.length=0;l.data.push(100);l.proportion.isShow=false;l.colors.push(l.defaultColor)}if(l.colors.length<l.data.length){for(var m=l.colors.length;m<l.data.length;m++){l.colors.push(window.ColorUtils.randomColor())}}if(/^0+$/.test(l.data.join(""))){l.data.push(100);l.proportion.isShow=false;l.colors.push(l.defaultColor)}var p=setInterval(function(){if(l.events&&l.events.drawing){l.events.drawing(n,o,l)}c.clearRect(0,0,k,j);c.fillStyle=l.background;c.fillRect(0,0,k,j);n=n+1/(l.animationTime*1000/l.frames)>o?o:n+1/(l.animationTime*1000/l.frames);b(n,o);if(n==o){clearInterval(p);if(l.events&&l.events.end){l.events.end(l)}}},1000/l.frames)}e.draw=function(){if(l.isDebug){console.log(l)}h=window.devicePixelRatio;c=f[0].getContext("2d");j=f.height()*h;k=f.width()*h;k=k==0?j:k;j=j==0?k:j;f.attr("height",j);f.attr("width",k);g.x=k/2;g.y=j/2;l.startAngle=l.startAngle?l.startAngle:parseFloat((2*Math.random()).toFixed(2));l.spacing*=h;var m=(l.spacing/2+l.proportion.lineLength)+window.ChartUtils.getTextWidth(c,"99.99%",l.proportion.fontSize*h,l.proportion.fontFamily);l.radius=l.proportion.isShow?(g.x<g.y?g.x-m:g.y-m):(g.x<g.y?g.x-l.spacing/2:g.y-l.spacing/2);i()};return e}})(jQuery);(function(a){a.fn.Scale=function(e){var g={};var h=a(this);var d=null;var n=0;var q=0;var k=1;var i=[];var m={x:0,y:0},c={x:0,y:0},p={x:0,y:0},o={x:0,y:0};var r={type:1,min:0,max:100,target:50,background:"transparent",frames:60,isAnimation:true,animationTime:5,isDebug:false,events:{start:function(s){},drawing:function(t,u,s){},end:function(s){}},tick:{colorType:0,valueType:0,width:1,defaultColor:"#CCCCCC",targetColor:"#ffc733",tickCount:100,textColor:"#999999",fontSize:12,fontFamily:"Microsoft YaHei"},cursor:{textColor:"white",fontSize:10,fontFamily:"Microsoft YaHei",background:"black",triangleColor:"black",textStart:"",textEnd:""},colors:[]};r=a.extend(true,r,e);function f(){var u;m.x=q*0.05;m.y=0;c.x=q*0.95;c.y=0;p.x=q*0.05;p.y=n;o.x=q*0.95;o.y=n;var t=n/3;var x=(c.x-m.x)/r.tick.tickCount;var v=0;var w=0;var y;for(u=0;u<=r.tick.tickCount;u++){y={};v=n/9;w=r.type==0?0:n/9;if(u%5==0){v=n*2/9;w=r.type==0?0:n/18}if(u%10==0){v=n/3;w=0;var s=parseFloat(((r.max-r.min)*u/r.tick.tickCount).toFixed(2));y.text={x:m.x+(c.x-m.x)*u/r.tick.tickCount,y:n*7/9,content:r.tick.valueType==0?s:s*100+"%"}}y.pointTop={x:m.x+x*u,y:t+w};y.pointBottom={x:m.x+x*u,y:t+w+v};i.push(y)}}function j(){if(r.isDebug){window.ChartUtils.drawPolygon(d,[m,c,o,p,m],"green",false,k);window.ChartUtils.drawLine(d,[{x:p.x,y:p.y/2},{x:o.x,y:o.y/2}],"green",k)}}function b(D){var z=D/(r.max-r.min);var v;for(v=0;v<=r.tick.tickCount;v++){var C=i[v];if(v<=r.tick.tickCount*z){window.ChartUtils.drawLine(d,[C.pointTop,C.pointBottom],r.tick.colorType==0?window.ColorUtils.getColorStop(0,r.tick.tickCount,v,r.colors):r.tick.targetColor,r.tick.width*k)}else{window.ChartUtils.drawLine(d,[C.pointTop,C.pointBottom],r.tick.defaultColor,r.tick.width*k)}if(C.text){window.ChartUtils.drawText(d,C.text.content,C.text.x,C.text.y,r.tick.textColor,r.tick.fontSize*k,r.tick.fontFamily,"center","top")}}var B=m.x+(c.x-m.x)*z;var u=n*13/18;var A=n*7/9;window.ChartUtils.drawPolygon(d,[{x:B,y:u},{x:B+(A-u),y:A},{x:B-(A-u),y:A}],r.cursor.triangleColor,true);var t=r.tick.valueType==0?parseFloat(D.toFixed(2)):parseFloat((D*100).toFixed(2))+"%";t=r.cursor.textStart+t+r.cursor.textEnd;var y=window.ChartUtils.getTextWidth(d,t,r.cursor.fontSize*k,r.cursor.fontFamily);var w=r.cursor.fontSize*k;var s=w/2;if(B-y/2-s<0){window.ChartUtils.drawRoundRect(d,0,n*5/18-w*1.2-s/2,y+s*2,w+s,s*2,r.cursor.background,true);window.ChartUtils.drawText(d,t,s,n*5/18,r.cursor.textColor,r.cursor.fontSize*k,r.cursor.fontFamily,"left","bottom")}else{if(B+y/2+s>q){window.ChartUtils.drawRoundRect(d,q-y-s*2,n*5/18-w*1.2-s/2,y+s*2,w+s,s*2,r.cursor.background,true);window.ChartUtils.drawText(d,t,q-s,n*5/18,r.cursor.textColor,r.cursor.fontSize*k,r.cursor.fontFamily,"right","bottom")}else{window.ChartUtils.drawRoundRect(d,B-y/2-s,n*5/18-w*1.2-s/2,y+s*2,w+s,s*2,r.cursor.background,true);window.ChartUtils.drawText(d,t,B,n*5/18,r.cursor.textColor,r.cursor.fontSize*k,r.cursor.fontFamily,"center","bottom")}}}function l(){if(r.events&&r.events.start){r.events.start(r)}var t=r.isAnimation?r.min:r.max;var u=r.target;var s=(r.max-r.min)/(r.animationTime*1000/r.frames);f();var v=setInterval(function(){if(r.events&&r.events.drawing){r.events.drawing(t,u,r)}d.clearRect(0,0,q,n);d.fillStyle=r.background;d.fillRect(0,0,q,n);t=t+s>u?u:t+s;j();b(t);if(t==u){clearInterval(v);if(r.events&&r.events.end){r.events.end(r)}}},1000/r.frames)}g.draw=function(){if(r.isDebug){console.log(r)}k=window.devicePixelRatio;d=h[0].getContext("2d");n=h.height()*k;q=h.width()*k;q=q==0?n:q;n=n==0?q:n;h.attr("height",n);h.attr("width",q);l()};return g}})(jQuery);(function(a){a.fn.Scatter=function(r){var t={};var b=a(this);var c=null;var i=0;var f=0;var p=1;var q={x:0,y:0};var o=null;var d={alpha:0.3,beta:1,background:"transparent",frames:60,isAnimation:true,animationTime:5,lineWidth:1,isDebug:false,events:{start:function(u){},drawing:function(w,v,x,u){},end:function(u){}},axis:{color:"#666666",width:1},point:{color:"#666666",radius:1,count:60,rang:20},line:{base:{width:1,color:"orange"},alpha:{width:1,color:"red"},beta:{width:1,color:"green"}},valueText:{type:2,fontSize:12,fontFamily:"Microsoft YaHei",color:"#333333"}};d=a.extend(true,d,r);function j(u){return d.beta*u+d.alpha*(i*3/4)}function l(u){return(u-d.alpha*(i*3/4))/d.beta}function e(u){return parseInt(u*Math.sqrt(Math.pow(d.beta,2)+1))}function g(){var B;q.x=f/2;q.y=i*3/4;var v=e(d.point.rang*p);if(!d.point.data){var C=f/2/d.point.count;d.point.data=[];for(B=1;B<d.point.count;B++){var z=C*B;var F=j(z);var x=F-Math.random()*v+Math.random()*v;d.point.data.push({x:q.x+z,y:q.y-x})}}var A=d.beta>0?Math.abs(l(i*3/4)/2):Math.abs(l(i/4)/2);if(A<5){A=l(i*3/4)}if(A>f/2){A=f/2/2}var G=j(A);var w=A;var E=j(0);var u=0;var D=j(0);o={point0:{x:q.x+A,y:q.y-G},point1:{x:q.x+w,y:q.y-E},point2:{x:q.x+u,y:q.y-D}}}function m(){window.ChartUtils.drawLine(c,[{x:0,y:q.y},{x:f,y:q.y}],d.axis.color,d.axis.width*p);window.ChartUtils.drawLine(c,[{x:q.x,y:0},{x:q.x,y:i}],d.axis.color,d.axis.width*p)}function k(v,x){var w=v/x;for(var u=0;u<d.point.data.length*w;u++){window.ChartUtils.drawCircle(c,d.point.data[u].x,d.point.data[u].y,d.point.radius*p,0,2,d.point.color,true)}}function n(u,w){var v=u/w;window.ChartUtils.drawLine(c,[{x:0,y:q.y-j(-f/2)},{x:f*v,y:q.y-j(-f/2+f*v)}],d.line.base.color,d.line.base.width*p)}function h(){if(d.valueText.type==0||d.valueText.type==2){window.ChartUtils.drawLine(c,[{x:q.x,y:q.y},o.point2],d.line.alpha.color,d.line.alpha.width*p)}if(d.valueText.type==1||d.valueText.type==2){window.ChartUtils.drawLine(c,[o.point0,o.point1,o.point2],d.line.beta.color,d.line.beta.width*p)}var u=(q.y+o.point2.y)/2;var v="";switch(d.valueText.type){case 0:v="Î± = "+d.alpha;break;case 1:v="Î² = "+d.beta;break;case 2:v="Î± = "+d.alpha+" Î² = "+d.beta}if(i*3/4*d.alpha<d.valueText.fontSize*p){u=q.y+(d.beta<0?-(d.valueText.fontSize*p):(d.valueText.fontSize*p))}window.ChartUtils.drawText(c,v,q.x+10*p,u,d.valueText.color,d.valueText.fontSize*p,d.valueText.fontFamily,"left","middle")}function s(){if(d.events&&d.events.start){d.events.start(d)}var u=d.isAnimation?0:1;var v=1;g();var w=setInterval(function(){if(d.events&&d.events.drawing){d.events.drawing("point",u,v,d)}c.clearRect(0,0,f,i);c.fillStyle=d.background;c.fillRect(0,0,f,i);u=u+1/(d.animationTime*0.8*1000/d.frames)>v?v:u+1/(d.animationTime*0.8*1000/d.frames);m();k(u,v);if(u==v){clearInterval(w);u=d.isAnimation?0:1;v=1;w=setInterval(function(){if(d.events&&d.events.drawing){d.events.drawing("line",u,v,d)}c.clearRect(0,0,f,i);c.fillStyle=d.background;c.fillRect(0,0,f,i);u=u+1/(d.animationTime*0.2*1000/d.frames)>v?v:u+1/(d.animationTime*0.2*1000/d.frames);m();k(1,1);n(u,v);if(u==v){clearInterval(w);h();if(d.events&&d.events.end){d.events.end(d)}}},1000/d.frames)}},1000/d.frames)}t.draw=function(){if(d.isDebug){console.log(d)}p=window.devicePixelRatio;c=b[0].getContext("2d");i=b.height()*p;f=b.width()*p;f=f==0?i:f;i=i==0?f:i;b.attr("height",i);b.attr("width",f);s()};return t}})(jQuery);(function(a){a.fn.LineMixBar=function(z){var B={};var e=a(this);var f=null;var p=0;var h=0;var x=1;var r=null;var q=0;var s=[];var b=[];var t=[];var d;var k=3;var v=0;var j={x:0,y:0},m={x:0,y:0},c={x:0,y:0},w={x:0,y:0};var n=[];var g={data:{line:[],bar:[],floatTitle:[],top:[]},legends:{bar:[],line:[],top:[]},units:{bar:[],line:[],top:[]},colors:{line:[],bar:[],top:[]},background:"transparent",isDebug:false,axis:{x:[],fontSize:12,fontFamily:"Microsoft YaHei",color:"#666666",lineColor:"#EEEEEE",lineWidth:1,textMarginTopX:5,activeLine:{width:1,color:"#999999"}},bar:{gap:2,max:1,min:0,defaultValue:"",width:undefined,type:0,defaultColor:"#CCCCCC"},line:{width:2,dashedLength:5,areaType:1,max:100,min:0,defaultValue:"",point:{radius:3,width:1,fill:"white"}},top:{fontSize:12,fontFamily:"Microsoft YaHei",color:"#666666",textMarginBottom:5}};g=a.extend(true,g,z);function l(){var F;var D;if(!g.axis.x.length){console.error("åæ ‡è½´æ•°æ®ä¸èƒ½ä¸ºç©ºï¼");return false}var L=g.top.fontSize*g.data.top.length*x*1.2+g.top.textMarginBottom*g.data.top.length*x;var C=p-(g.axis.fontSize+g.axis.textMarginTopX)*x*g.axis.x[0].length*1.2;j={x:0,y:L};m={x:h,y:L};c={x:0,y:C};w={x:h,y:C};var J=h/g.axis.x.length;if(g.isDebug){console.log("åŒºé—´å®½åº¦ï¼š"+J)}for(F=0;F<g.axis.x.length;F++){n.push([{x:J/2+(J*F),y:L},{x:J/2+(J*F),y:w.y}])}if(g.data.bar.length){q=g.bar.width?(g.bar.width*x):((J-(q*g.axis.x.length-1))/(g.bar.type==1?3:(g.data.bar[0].length+1)));if(g.isDebug){console.log("æŸ±çŠ¶å›¾å®½åº¦ï¼š"+q)}var G;var K=g.bar.max-g.bar.min;var H;switch(g.bar.type){case 0:H=(J-(q*g.data.bar[0].length+g.bar.gap*x*(g.data.bar[0].length-1)))/2;if(g.isDebug){console.log("æŸ±çŠ¶å›¾åç§»ï¼š"+H)}for(F=0;F<g.data.bar.length;F++){G=[];for(D=0;D<g.data.bar[F].length;D++){if(!g.data.bar[F][D]){continue}if(g.data.bar[F][D]>g.bar.max||g.data.bar[F][D]<g.bar.min){console.warn("æŸ±çŠ¶å›¾æ•°å€¼è¶…å‡ºèŒƒå›´ï¼["+F+"]["+D+"]")}G.push([[{x:H+J*F+(g.bar.gap*x+q)*D,y:c.y},{x:H+J*F+(g.bar.gap*x+q)*D,y:(g.data.bar[F][D]>g.bar.max||g.data.bar[F][D]<g.bar.min)?c.y:c.y-(c.y-j.y)*g.data.bar[F][D]/K},{x:H+J*F+(g.bar.gap*x+q)*D+q,y:(g.data.bar[F][D]>g.bar.max||g.data.bar[F][D]<g.bar.min)?c.y:c.y-(c.y-j.y)*g.data.bar[F][D]/K},{x:H+J*F+(g.bar.gap*x+q)*D+q,y:c.y}]])}s.push(G)}break;case 1:var E;H=(J-q)/2;if(g.isDebug){console.log("æŸ±çŠ¶å›¾åç§»ï¼š"+H)}for(F=0;F<g.data.bar.length;F++){G=[];var I=0;for(D=0;D<g.data.bar[F].length;D++){if(g.data.bar[F][D]>g.bar.max||g.data.bar[F][D]<g.bar.min){console.warn("æŸ±çŠ¶å›¾æ•°å€¼è¶…å‡ºèŒƒå›´ï¼["+F+"]["+D+"]")}I+=g.data.bar[F][D]}if(I==0){g.data.bar[F].push(g.bar.max);I=g.bar.max;if(g.data.bar[F].length>g.colors.bar.length){g.colors.bar.push(g.bar.defaultColor)}}for(D=0;D<g.data.bar[F].length;D++){E=c.y;if(D>0){E=G[0][D-1][1].y}(G[0]=G[0]||[]).push([{x:H+J*F,y:E},{x:H+J*F,y:E-(c.y-j.y)*((g.data.bar[F][D]>g.bar.max||g.data.bar[F][D]<g.bar.min)?g.bar.min:g.data.bar[F][D])/I},{x:H+J*F+q,y:E-(c.y-j.y)*((g.data.bar[F][D]>g.bar.max||g.data.bar[F][D]<g.bar.min)?g.bar.min:g.data.bar[F][D])/I},{x:H+J*F+q,y:E}])}s.push(G)}console.log(s);break}}K=g.line.max-g.line.min;for(F=0;F<g.data.line.length;F++){G=[];for(D=0;D<g.data.line[F].length;D++){G.push({x:n[D][0].x,y:(g.data.line[F][D]<g.line.min||g.data.line[F][D]>g.line.max)?c.y:c.y-(c.y-j.y)*g.data.line[F][D]/K})}b.push(G)}v=h/g.axis.x.length;return true}function u(){var C;if(g.isDebug){window.ChartUtils.drawPolygon(f,[j,m,w,c,j],"green",false,x);for(C=0;C<n.length;C++){window.ChartUtils.drawLine(f,n[C],"gray",x)}}window.ChartUtils.drawLine(f,[{x:j.x,y:j.y},{x:c.x,y:c.y},{x:w.x,y:w.y}],g.axis.lineColor,g.axis.lineWidth*x);return true}function i(){var G;var F;var E;for(G=0;G<s.length;G++){for(F=0;F<s[G].length;F++){for(E=0;E<s[G][F].length;E++){window.ChartUtils.drawPolygon(f,s[G][F][E],g.bar.type==0?g.colors.bar[F]:g.colors.bar[E],true,x)}}}for(G=0;G<b.length;G++){var D=b[G];var H=D.length;window.ChartUtils.drawLine(f,D,g.colors.line[G],g.line.width*x);window.ChartUtils.drawDashedLine(f,[{x:0,y:D[0].y},{x:D[0].x,y:D[0].y}],g.colors.line[G],g.line.dashedLength*x,g.line.width*x);window.ChartUtils.drawDashedLine(f,[{x:D[H-1].x,y:D[H-1].y},{x:h,y:D[H-1].y}],g.colors.line[G],g.line.dashedLength*x,g.line.width*x);for(F=0;F<b[G].length;F++){window.ChartUtils.drawCircle(f,b[G][F].x,b[G][F].y,g.line.point.radius*x,0,2,g.line.point.fill,true);window.ChartUtils.drawCircle(f,b[G][F].x,b[G][F].y,g.line.point.radius*x,0,2,g.colors.line[G],false,g.line.point.width*x)}switch(g.line.areaType){case 0:break;case 1:(t=[]).push(D.concat());t[G].unshift({x:D[0].x,y:c.y});t[G].push({x:D[H-1].x,y:c.y});break;case 2:(t=[]).push(D.concat());t[G].unshift({x:0,y:D[0].y});t[G].unshift({x:0,y:c.y});t[G].push({x:h,y:D[H-1].y});t[G].push({x:h,y:c.y});break}}if(g.line.areaType>0){for(G=0;G<t.length;G++){var C=f.createLinearGradient(j.x,j.y,c.x,c.y);C.addColorStop(0,window.ColorUtils.opacityColor(g.colors.line[G],0.5));C.addColorStop(1,window.ColorUtils.opacityColor(g.colors.line[G],0));window.ChartUtils.drawPolygon(f,t[G],C,true,x)}}for(G=0;G<g.axis.x.length;G++){for(F=0;F<g.axis.x[G].length;F++){window.ChartUtils.drawText(f,g.axis.x[G][F],n[G][1].x,n[G][1].y+(g.axis.fontSize*F+g.axis.textMarginTopX*(F+1))*x,g.axis.color,g.axis.fontSize*x,g.axis.fontFamily,"center","top")}}for(G=0;G<g.data.top.length;G++){for(F=0;F<g.data.top[G].length;F++){window.ChartUtils.drawText(f,g.data.top[G][F],n[F][0].x,(g.top.fontSize+g.top.textMarginBottom)*G*x,g.colors.top[G],g.top.fontSize*x,g.top.fontFamily,"center","top")}}return true}function y(H){var D;var C;o();u();i();window.ChartUtils.drawLine(f,n[H],g.axis.activeLine.color,g.axis.activeLine.width*x);var G="";for(D=0;D<g.legends.top.length;D++){if(g.data.top[D]){G+='<li><span class="icon" style="background:'+g.colors.top[D]+'"></span><span>'+g.legends.top[D]+"ï¼š"+g.data.top[D][H]+(g.units.top[D]||"")+"</span></li>"}}for(D=0;D<g.legends.line.length;D++){if(g.data.line[D]){G+='<li><span class="icon" style="background:'+g.colors.line[D]+'"></span><span>'+g.legends.line[D]+"ï¼š"+((g.data.line[D][H]<g.line.min||g.data.line[D][H]>g.line.max)?g.line.defaultValue:g.data.line[D][H]+(g.units.line[D]||""))+"</span></li>"}}for(D=0;D<g.legends.bar.length;D++){C=g.bar.type==1?g.legends.bar.length-1-D:D;if(g.data.bar[C]){G+='<li><span class="icon" style="background:'+g.colors.bar[C]+'"></span><span>'+g.legends.bar[C]+"ï¼š"+((g.data.bar[H][C]<g.bar.min||g.data.bar[H][C]>g.bar.max)?g.bar.defaultValue:g.data.bar[H][C]+(g.units.bar[C]||""))+"</span></li>"}}d.find(".title").text(g.data.floatTitle[H]);d.find("ul").empty().append(G);var F;if(!b[0]){F=p/2/x}else{F=b[0][H].y/x-k}var E=n[H][0].x/x+k;if(F+d.outerHeight()>c.y/x){F=w.y/x-d.outerHeight()-k}if(E+d.outerWidth()>w.x/x){E=w.x/x-d.outerWidth()-k}d.css({opacity:1,top:F,left:E})}function o(){f.clearRect(0,0,h,p);f.fillStyle=g.background;f.fillRect(0,0,h,p)}function A(){if(l()&&u()&&i()){e.on("click",function(C){var D=parseInt(C.offsetX*x/v);y(D)})}}B.draw=function(){if(g.isDebug){console.log(g)}x=window.devicePixelRatio;f=e[0].getContext("2d");if(g.bar.width){var E=e.width();var D=(g.bar.width*(g.bar.type==1?3:(g.data.bar[0].length+1))+g.bar.gap*(g.bar.type==1?0:(g.data.bar[0].length-1)))*g.data.bar.length;e.css({width:D>E?D:E})}p=e.height()*x;h=e.width()*x;h=h==0?p:h;p=p==0?h:p;e.attr("height",p);e.attr("width",h);r=e.wrap('<div class="line-mix-bar"><div style="overflow: auto"><div class="line-mix-bar-canvas"></div></div></div>').parents(".line-mix-bar");var H=a('<div><ul  class="line-mix-bar-legends"></ul></div>');var G=H.find(".line-mix-bar-legends");var F=r.find(".line-mix-bar-canvas");var C;for(C=0;C<g.legends.top.length;C++){G.append('<li><span class="icon bar" style="background: '+g.colors.top[C]+'"></span><span>'+g.legends.top[C]+"</span></li>")}for(C=0;C<g.legends.bar.length;C++){G.append('<li><span class="icon bar" style="background: '+g.colors.bar[C]+'"></span><span>'+g.legends.bar[C]+"</span></li>")}for(C=0;C<g.legends.line.length;C++){G.append('<li><span class="icon line" style="background: '+g.colors.line[C]+'"></span><span>'+g.legends.line[C]+"</span></li>")}d=a('<div class="line-mix-bar-float-msg"><div class="title"></div><ul></ul></div>').css({top:0,left:0});F.append(d);r.append(H);A()};return B}})(jQuery);(function(a){a.fn.Radar=function(u){var x={};var c=a(this);var d=null;var m=0;var g=0;var t=1;var q={x:0,y:0};var f=0;var n=[];var p=[];var v=[];var r;var h;var j={x:0,y:0},l={x:0,y:0},b={x:0,y:0},s={x:0,y:0};var e={data:[],background:"transparent",min:0,max:10,dimensions:{data:[],fontSize:12,fontFamily:"Microsoft YaHei",color:"#666666",margin:5},colors:{base:{line:"#ced0d1",background:"#e2f6ff"},data:{line:"#1799d3",background:"#1799d3",opacity:0.5}},frames:60,isAnimation:true,animationTime:5,isDebug:false,events:{start:function(y){},drawing:function(z,A,y){},end:function(y){}}};e=a.extend(true,e,u);function k(){var B;var E=0;var A;for(B=0;B<e.dimensions.data.length;B++){A=window.ChartUtils.getTextWidth(d,e.dimensions.data[B],e.dimensions.fontSize*t,e.dimensions.fontFamily);E=E>A?E:A}var D=e.dimensions.fontSize*t*1.2;j={x:E,y:D};l={x:g-E,y:D};b={x:E,y:m-D};s={x:g-E,y:m-D};q.x=g/2;q.y=m/2;var z=(l.x-j.x)/2;var y=(b.y-j.y)/2;f=z>y?y:z;h={top:m/2,right:g/2,bottom:m/2,left:g/2};if(e.dimensions.data.length%2){r=3/2}else{r=3/2+1/e.dimensions.data.length}for(B=0;B<e.dimensions.data.length;B++){var C=(r+2/e.dimensions.data.length*B)*Math.PI;n.push({x:q.x+Math.cos(C)*f,y:q.y+Math.sin(C)*f});p.push([q,n[B]]);h.top=h.top<n[B].y?h.top:n[B].y;h.right=h.right>n[B].x?h.right:n[B].x;h.bottom=h.bottom>n[B].y?h.bottom:n[B].y;h.left=h.left<n[B].x?h.left:n[B].x;v.push({x:q.x+Math.cos(C)*(f+e.dimensions.margin*t),y:q.y+Math.sin(C)*(f+e.dimensions.margin*t)})}console.log(h)}function o(){var z;if(e.isDebug){window.ChartUtils.drawPolygon(d,[j,l,s,b,j],"green",false,t);window.ChartUtils.drawLine(d,[{x:b.x,y:b.y/2},{x:s.x,y:s.y/2}],"green",t);window.ChartUtils.drawCircle(d,q.x,q.y,f,0,2,"green",false,1)}window.ChartUtils.drawPolygon(d,n,e.colors.base.background,true,t);window.ChartUtils.drawPolygon(d,n,e.colors.base.line,false,t);for(z=0;z<p.length;z++){window.ChartUtils.drawLine(d,p[z],e.colors.base.line,t)}for(z=0;z<v.length;z++){var A="center";var y="middle";switch(true){case v[z].x-q.x<-0.00001:A="right";break;case Math.abs(v[z].x-q.x)<0.00001:A="center";break;case v[z].x-q.x>0.00001:A="left";break}switch(true){case Math.abs(n[z].y-h.top)<0.00001:y="bottom";break;case Math.abs(n[z].y-h.bottom)<0.00001:A="center";y="top";break}window.ChartUtils.drawText(d,e.dimensions.data[z],v[z].x,v[z].y,e.dimensions.color,e.dimensions.fontSize*t,e.dimensions.fontFamily,A,y)}}function i(B,E){var A;var z=[];var C=e.max-e.min;var y=f/C*B/E;for(A=0;A<e.dimensions.data.length;A++){var D=(r+2/e.dimensions.data.length*A)*Math.PI;z.push({x:q.x+Math.cos(D)*e.data[A]*y,y:q.y+Math.sin(D)*e.data[A]*y})}for(A=0;A<e.dimensions.data.length;A++){window.ChartUtils.drawCircle(d,z[A].x,z[A].y,2*t,0,2,e.colors.data.line,false,2*t)}window.ChartUtils.drawPolygon(d,z,window.ColorUtils.opacityColor(e.colors.data.background,e.colors.data.opacity),true,t);window.ChartUtils.drawPolygon(d,z,e.colors.data.line,false,t)}function w(){if(e.events&&e.events.start){e.events.start(e)}var y=e.isAnimation?0:1;var z=1;if(e.dimensions.data.length<3){throw new Error("ç»´åº¦å¿…é¡»å¤§äºŽ3")}k();var A=setInterval(function(){if(e.events&&e.events.drawing){e.events.drawing(y,z,e)}d.clearRect(0,0,g,m);d.fillStyle=e.background;d.fillRect(0,0,g,m);y=y+1/(e.animationTime*1000/e.frames)>z?z:y+1/(e.animationTime*1000/e.frames);o();i(y,z);if(y==z){clearInterval(A);if(e.events&&e.events.end){e.events.end(e)}}},1000/e.frames)}x.draw=function(){if(e.isDebug){console.log(e)}t=window.devicePixelRatio;d=c[0].getContext("2d");m=c.height()*t;g=c.width()*t;g=g==0?m:g;m=m==0?g:m;c.attr("height",m);c.attr("width",g);w()};return x}})(jQuery);