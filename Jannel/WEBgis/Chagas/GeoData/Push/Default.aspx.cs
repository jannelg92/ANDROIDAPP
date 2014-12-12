using System;
using System.Collections.Generic;

using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;

public partial class Default : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

        try
        {
            SqlConnection connection = new SqlConnection("user id=jannel.gonzales;" +
                                            "password=two013loveKILLS;" +
                                            "server=GEOG489-03.tamu.edu;" +
                                            "database=Lab6; " +
                                            "connection timeout=30");


            /*
             * 
             * After creating a database called Lab6, run this SQL command
             * 
             * USE [Lab6]
             * CREATE TABLE [dbo].[countries_borders](
             * [WKT] [varchar](max) NULL,
             * [NAME] [varchar](50) NULL,
             * [POP] [varchar](50) NULL,	
             * [Geom] [geometry] NULL) 
             * ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
             * 
             * 
             */


            string queryWkt = Request["wkt2222"];
            string queryName = Request["name2222"];
            string queryPop = Request["pop2222"];


            string sql = "INSERT INTO [Lab6].[dbo].[countries_borders]";
            sql += " (WKT, NAME, POP) ";
            sql += " VALUES  ";
            sql += " (@wkt, @name, @pop) ";

            SqlCommand command = new SqlCommand(sql, connection);
            command.Connection.Open();

           

            SqlParameter parameter1 = new SqlParameter("@wkt", queryWkt);
            command.Parameters.Add(parameter1);

            SqlParameter parameter2 = new SqlParameter("@name", queryName);
            command.Parameters.Add(parameter2);

            SqlParameter parameter3 = new SqlParameter("@pop", queryPop);
            command.Parameters.Add(parameter3);


           
            int result = command.ExecuteNonQuery();

            Response.Write("{ ");
            Response.Write(Environment.NewLine);
            Response.Write("\"result\": \"" + result + "\"");
            Response.Write(Environment.NewLine);

            Response.Write("} ");

            // you should probably make this into a SQL Geoemtry column!
            // update [Lab6].[dbo].[countries_borders] set [Geom] = geometry::STGeomFromText ( WKT, 4326 )

        }
        catch (Exception ex)
        {
            Response.Write("Exception ocurred: " + ex.Message);
        }
    }
}