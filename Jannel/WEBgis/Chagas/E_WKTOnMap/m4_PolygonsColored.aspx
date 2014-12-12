﻿<%@ Page Language="C#" AutoEventWireup="true" CodeFile="m4_PolygonsColored.aspx.cs" Inherits="m3_ShowGMLPolygonFromFileWithOptionsColored" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<style>
      html, body, #map-canvas {
        height: 500px;
		width: 500px;
        margin: 0px;
        padding: 0px
      }
    </style>
	
	
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript"  src="http://geojam.net/static/wicket/wicket.src.js" ></script>
<script type="text/javascript"  src="http://geojam.net/static/wicket/wicket-gmap3.src.js" ></script>
<script type="text/javascript"  src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<!-- This file will be wherever you put the folder locally on your server. The resolveURL part says to find it from the ~ of the site (the root) -->
<script type="text/javascript"  src="http://geog489-03.tamu.edu/Data/Geog489Lab6/scripts/csvParser.js"></script>

<script type="text/javascript" >

    var map;
    var features = new Array();

    // update this to be the correct path in your site
    var csvLocation = "http://geog489-03.tamu.edu/Data/Geog489Lab6/data/Countries/countries_bordersNorthAmerica.csv";

    $(document).ready(function () {
        $("button").click(function () {

            MapWKT();

        });
    });


function handleDone(data, textStatus)
{
    $.each(data.items, );

/* }

function HandleItem(array, items)
{
*/
    // Create a new Wicket instance
    var wkt = new Wkt.Wkt();

    var array = $.getCSVParser()(data);
    if (array)
    {


        for (var j = 1; j < array.length; j++)
        {

            var wktVal = array[j][0];

            var pop = parseInt(array[j][7]);
            var name = array[j][5];

            wkt.read(wktVal);

            var obj = wkt.toObject(map.defaults); // Make an object

            if (Wkt.isArray(obj))
            {
                for (i in obj)
                {
                    if (obj.hasOwnProperty(i) && !Wkt.isArray(obj[i]))
                    {

                        var color = '';
                        if (pop <= 32270507)
                         {
                            color = '#e5f5f9'
                          }
                        else if (pop <= 104266392)
                         {
                            color = '#99d8c9'
                         }
                        else if (pop <= 299846449)
                          {
                            color = '#2ca25f';
                         }


                        //polygon options
                        var options = {
                            clickable: true,
                            fillColor: color,
                            fillOpacity: 0.5,
                            geodesic: true,
                            map: map,
                            strokeColor: '#000000',
                            strokeOpacity: 1,
                            strokeWeight: 1,
                            zIndex: 99
                        }

                        obj[i].setOptions(options);
                        features.push(obj[i]);

                       

                        var contentString = '';
                        contentString += '<div id="content">';
                        contentString += '<div id="siteNotice">';
                        contentString += '</div>';
                        contentString += '<h1 id="firstHeading" class="firstHeading">' + name + '</h1>';
                        contentString += '<div id="bodyContent">';
                        contentString += '<p>';
                        contentString += pop
                        contentString += '</p>';
                        contentString += '</div>';
                        contentString += '</div>';


                        var infowindow = new google.maps.InfoWindow({
                            content: contentString
                        });

                        google.maps.event.addListener(obj[i], 'click', function (event)
                        {
                            var v = name;
                            var p = pop;
                            infowindow.setPosition(event.latLng);
                            infowindow.open(map);
                        });


                    }
                }
            }
            else
            {
                obj.setMap(map); // Add it to the map
                features.push(obj);

                var color = '';
                if (pop <= 32270507)
                {
                    color = '#e5f5f9'
                }
                else if (pop <= 104266392)
                {
                    color = '#99d8c9'
                }
                else if (pop <= 299846449)
                {
                    color = '#2ca25f';
                }


                //polygon options
                var options = {
                    clickable: true,
                    fillColor: color,
                    fillOpacity: 0.5,
                    geodesic: true,
                    map: map,
                    strokeColor: '#000000',
                    strokeOpacity: 1,
                    strokeWeight: 1,
                    zIndex: 99
                }

                obj.setOptions(options);
                features.push(obj);

                var pop = parseInt(array[j][7]);
                var name = array[j][5];

                var contentString = '';
                contentString += '<div id="content">';
                contentString += '<div id="siteNotice">';
                contentString += '</div>';
                contentString += '<h1 id="firstHeading" class="firstHeading">' + name + '</h1>';
                contentString += '<div id="bodyContent">';
                contentString += '<p>';
                contentString += pop
                contentString += '</p>';
                contentString += '</div>';
                contentString += '</div>';

                var infowindow = new google.maps.InfoWindow({
                    content: contentString
                });

                google.maps.event.addListener(obj, 'click', function (event)
                {
                    infowindow.setPosition(event.latLng);
                    infowindow.open(map);
                });
            }
        }
    }
}

function MapWKT()
{

    $.get(csvLocation, handleDone);
    
}


		
</script>



 

 
 <script type="text/javascript" >
     
     function initialize() {
         var mapOptions = {
             zoom: 3,
             center: new google.maps.LatLng(31.19055265, -101.46077002)
         };

         map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
     }

     google.maps.event.addDomListener(window, 'load', initialize);
 </script>
 

</head>
<body>


<div id="map-canvas"></div>



<button>Map North America</button>


</body>
</html>