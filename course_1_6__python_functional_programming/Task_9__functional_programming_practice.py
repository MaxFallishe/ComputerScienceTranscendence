# Function gets as a parameter an array of N integers (N >= 2) indexed from zero, in which each element with an even
# index contains the speed in kilometers per hour, and each element with an odd index contains the time elapsed since
# the beginning of the trip, in hours. The function returns the total distance traveled.

def odometer(notes: list[int]) -> int:
    speed = notes[0::2]
    hours = [0] + notes[1::2]
    hours = [hours[i] - hours[i - 1] for i in range(1, len(hours))]

    return sum(map(lambda x, y: x * y, hours, speed))

