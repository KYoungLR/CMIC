import chance from 'chance';

const range = len => {
  const arr = []
  for (let i = 0; i < len; i++) {
    arr.push(i)
  }
  return arr
}

const newData = () => {
  return {
  	accountName: new Chance().company(),
  	accountNumber: new Chance().string({ length: 8, numeric: true }),
  	producerName: new Chance().company(),
  	producerCode: new Chance().string({ length: 4, numeric: true }),
  	inForcePolicy: new Chance().integer({ min: 9, max: 100 }),
  	futurePolicy: new Chance().integer({ min: 9, max: 100 }),
  	expiredPolicy: new Chance().integer({ min: 9, max: 100 }),
  	totalBilledPremium: new Chance().floating({ min: 0, max: 100000, fixed: 2 }),
  }
}

export default function generateData(...lens) {
  const generateDataLevel = (depth = 0) => {
    const len = lens[depth]
    return range(len).map(d => {
      return {
        ...newData(),
        subRows: lens[depth + 1] ? generateDataLevel(depth + 1) : undefined,
      }
    })
  }

  return generateDataLevel()
}
