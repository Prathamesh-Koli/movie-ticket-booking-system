import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom"
import { Container, Card, Button, Spinner, Alert, Row, Col } from "react-bootstrap"
import axios from "axios"
<<<<<<< HEAD
import '../styles/TicketPage.css'
=======
>>>>>>> d1bc5bc36f511c3e2641c2081d7ae10454cc0ace

const TicketPage = () => {
  const { bookingId } = useParams()
  const navigate = useNavigate()
  const [ticket, setTicket] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState("")

  useEffect(() => {
    const fetchTicket = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/bookings/${bookingId}`)
        setTicket(response.data)
      } catch (err) {
        console.error("Error fetching ticket:", err)
        setError("Could not fetch ticket details. Please try again.")
      } finally {
        setLoading(false)
      }
    }

    fetchTicket()
  }, [bookingId])

  if (loading) {
    return (
      <Container className="py-5 text-center">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
      </Container>
    )
  }

  if (error) {
    return (
      <Container className="py-5 text-center">
        <Alert variant="danger">{error}</Alert>
        <Button variant="primary" onClick={() => navigate("/")}>Back to Home</Button>
      </Container>
    )
  }

  if (!ticket) {
    return (
      <Container className="py-5 text-center">
        <Alert variant="warning">No ticket details found.</Alert>
        <Button variant="primary" onClick={() => navigate("/")}>Browse Movies</Button>
      </Container>
    )
  }

  return (
    <Container className="py-4">
      <Row className="justify-content-center">
        <Col md={8}>
<<<<<<< HEAD
<Card className="ticket-card"
  style={{ backgroundColor: '#afada7ff' }} //f1c166ff
>
  <Card.Header className="text-center">
    <h4>BooKar</h4>
  </Card.Header>

  <div className="perforated-line"></div>

  <Card.Body className="ticket-body-centered">
    <div className="ticket-row">
      <span className="ticket-label">Booking ID:</span>
      <span className="ticket-value">{ticket.id}</span>
    </div>
    <div className="ticket-row">
      <span className="ticket-label">Payment ID:</span>
      <span className="ticket-value">{ticket.paymentId}</span>
    </div>
    <div className="ticket-row">
      <span className="ticket-label">User Name:</span>
      <span className="ticket-value">{ticket.username}</span>
    </div>
    <div className="ticket-row">
      <span className="ticket-label">Movie Name:</span>
      <span className="ticket-value">{ticket.movieName}</span>
    </div>
    <div className="ticket-row">
      <span className="ticket-label">Theatre Name:</span>
      <span className="ticket-value">{ticket.theatreName}</span>
    </div>
    <div className="ticket-row">
      <span className="ticket-label">Show Date:</span>
      <span className="ticket-value">{ticket.movieDate}</span>
    </div>
    <div className="ticket-row">
      <span className="ticket-label">Show Time:</span>
      <span className="ticket-value">{ticket.showTime}</span>
    </div>
    <div className="ticket-row">
      <span className="ticket-label">Seat Numbers:</span>
      <span className="ticket-value">{ticket.seatNumbers?.join(", ")}</span>
    </div>
  </Card.Body>
</Card>

<div className="button-container">
  <Button variant="primary" onClick={() => navigate("/")}>
    Book Another Ticket
  </Button>
</div>



=======
          <Card className="shadow">
            <Card.Header className="text-center">
              <h4>Your Movie Ticket</h4>
            </Card.Header>
            <Card.Body>
              <p><strong>Booking ID:</strong> {ticket.id}</p>
              <p><strong>Payment ID:</strong> {ticket.paymentId}</p>
              <p><strong>Reservation ID:</strong> {ticket.reservationId}</p>
              <p><strong>User ID:</strong> {ticket.userId}</p>
              <p><strong>Booking Time:</strong> {new Date(ticket.bookingTime).toLocaleString()}</p>
              <hr />
              <Button variant="primary" onClick={() => navigate("/")}>Book Another Ticket</Button>
            </Card.Body>
          </Card>
>>>>>>> d1bc5bc36f511c3e2641c2081d7ae10454cc0ace
        </Col>
      </Row>
    </Container>
  )
}

export default TicketPage
