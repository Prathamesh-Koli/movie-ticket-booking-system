import { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom"
import { Container, Card, Button, Spinner, Alert, Row, Col } from "react-bootstrap"
import axios from "axios"
import '../styles/TicketPage.css'

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
        </Col>
      </Row>
    </Container>
  )
}

export default TicketPage
