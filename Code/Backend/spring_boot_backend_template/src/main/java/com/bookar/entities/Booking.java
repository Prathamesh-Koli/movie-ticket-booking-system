package com.bookar.entities;


	import jakarta.persistence.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "bookings")
	public class Booking {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "booking_id")
		private Long bookingId;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "user_id", nullable = false)
		private User user;

		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "reservation_id", nullable = false)
		private Reservation reservation;

		@Column(name = "payment_id", nullable = false)
		private String paymentId;

		@Column(name = "booking_time", nullable = false)
		private LocalDateTime bookingTime;

		// Constructors
		public Booking() {}

		public Booking(User user, Reservation reservation, String paymentId, LocalDateTime bookingTime) {
		    this.user = user;
		    this.reservation = reservation;
		    this.paymentId = paymentId;
		    this.bookingTime = bookingTime;
		}

		// Getters and Setters
		public Long getBookingId() {
		    return bookingId;
		}

		public void setBookingId(Long bookingId) {
		    this.bookingId = bookingId;
		}

		public User getUser() {
		    return user;
		}

		public void setUser(User user) {
		    this.user = user;
		}

		public Reservation getReservation() {
		    return reservation;
		}

		public void setReservation(Reservation reservation) {
		    this.reservation = reservation;
		}

		public String getPaymentId() {
		    return paymentId;
		}

		public void setPaymentId(String paymentId) {
		    this.paymentId = paymentId;
		}

		public LocalDateTime getBookingTime() {
		    return bookingTime;
		}

		public void setBookingTime(LocalDateTime bookingTime) {
		    this.bookingTime = bookingTime;
		}

}
