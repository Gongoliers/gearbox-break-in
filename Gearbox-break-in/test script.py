seconds = 1
m_speed = 0.3
m_RampUp = 5
m_RampDown = 2
m_timeToReverse = 15
m_reverse = False

def getSpeed():
    global seconds, m_reverse
    speed = m_speed
    if (seconds < m_RampUp):
        speed = (speed*(seconds/m_RampUp))
    if ((seconds+m_RampDown) > m_timeToReverse):
        speed = (speed*((m_timeToReverse-seconds)/m_RampDown))
    if (seconds > m_timeToReverse):
        if (m_reverse):
            m_reverse = False
        else:
            m_reverse = True
        seconds = 0
    speed = round(speed, 3)
    if (m_reverse):
        return -speed
    else:
        return speed
        
while True:
    print(getSpeed())
    seconds += .02
