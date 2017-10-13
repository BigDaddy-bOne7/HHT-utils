/**
 * Created by Administrator on 2016/11/25.
 */
function getStyle( obj , attr){
    if(obj.currentStyle){
        return obj.currentStyle[attr];
    }else{
        return getComputedStyle(obj , false)[attr];
    }
}

function slowMove(obj , json , fnEnd){
    clearInterval(obj.timer);
    obj.timer = setInterval(function(){
        var bAllStop = true ;
        for(var attr in json){
            if(attr == "opacity"){
                var curStyle = Math.round(getStyle(obj, attr) * 100);
            }else{
                var curStyle = parseInt(getStyle(obj, attr));
            }
            var iSpeed = (json[attr] - curStyle)/6;
            iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);

            if(curStyle != json[attr]){
                bAllStop = false;
            }

            if(attr == "opacity"){
                obj.style[attr] = (curStyle + iSpeed)/100;
                obj.style.filter = "alpha(opacity:" + (curStyle + iSpeed) + ")";
            }else{
                obj.style[attr] = curStyle + iSpeed + "px";
            }
        }
        if(bAllStop){
            clearInterval(obj.timer);
            if(fnEnd){
                fnEnd();
            }
        }
    },5000);
}