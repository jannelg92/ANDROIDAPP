// Users
//		Participants
		private int	Id { get; set; }
		public String Guid { get; set; }
		public String FirstName { get; set; }
		public String Email { get; set; }
		public String LastName { get; set; }
		public String UIN { get; set; }

		
// Users
// 		Professors
		private int	Id { get; set; } 
		public String Guid { get; set; }
		public String FirstName { get; set; }
		public String Email { get; set; }
		public String LastName { get; set; }

// CheckIns
		
        public int Id { get; set; }
        public string Guid { get; set; }
        public string EventGuid { get; set; }
        public string ParticipantGuid { get; set; }
        public DateTime Added { get; set; }


// Classes

		private int	Id
		public String Guid
		public String ProfessorGuid
		public String ClassTitle
		public String ClassCode
		public DateTime added 
			
			
// Course Credits
		
        public int Id { get; set; }
        public string Guid { get; set; }
        public string EventGuid { get; set; }
        public string ClassGuid { get; set; }
        public DateTime Added { get; set; }
	
			
// Event Variables

			private int Id { get; set; } 
            private String Guid { get; set; }
            private String UserGuid { get; set; }
            public String Title { get; set; }
            public String Abstract { get; set; }
            public DateTime Added { get; set; }
            public Int Capacity { get; set; }
            public DateTime Date { get; set; }
            public Int Duration { get; set; }
            public String EventName { get; set; }
            public String LocationBuilding { get; set; }
            public String LocationRoom { get; set; }
            public String QRCode { get; set; }
            public Boolean RequiresRSVP { get; set; }
            public String SpeakerEmail { get; set; }
            public String SpeakerFirstName { get; set; }
            public String SpeakerLastName { get; set; }
            public String Time { get; set; }


// Notify Variables
		public int ObjectId { get; set; }
        public string UserId { get; set; }
        public string UserEmail { get; set; }
        public SqlGeometry Shape { get; set; }
