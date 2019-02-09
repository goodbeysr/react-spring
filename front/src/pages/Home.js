import React from 'react';

import { Container, Col, Row} from 'reactstrap'

export default class Home extends React.Component {
  render() {
    return (
        <Container fluid>
            <Row>
                <Col sm='12'>
                <h1>Bienvenu</h1>
                </Col>
            </Row>
        </Container>
    );
  }
}